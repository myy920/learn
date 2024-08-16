package com.myy.util.page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class Query {

    private static final String CONDITION = "CONDITION";
    private static final String ORDER = "ORDER";
    private static final String LIMIT = "LIMIT";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate = JdbcTemplateService.get();

    @Component
    public static class JdbcTemplateService implements ApplicationContextAware {

        private static ApplicationContext APPLICATION_CONTEXT;

        @Override
        public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
            APPLICATION_CONTEXT = applicationContext;
        }

        public static NamedParameterJdbcTemplate get() {
            return APPLICATION_CONTEXT.getBean(NamedParameterJdbcTemplate.class);
        }

    }

    private String originSql;

    public final List<Part> parts = new ArrayList<>();

    public final MapSqlParameterSource parameterSource = new MapSqlParameterSource();

    public Query(@Language("sql") String originSql) {
        this.originSql = originSql;
    }

    @Getter
    @AllArgsConstructor
    public static class Part {

        private static final String AND = "AND";

        private static final String DEFAULT_CONDITION_SQL = " true ";

        private final String name;

        private final PartType partType;

        private final StringBuilder stringBuilder = new StringBuilder();

        public Part append(String partSql) {
            stringBuilder.append(' ').append(partSql).append(' ');
            return this;
        }

        public Part and(String partSql) {
            return append(AND).append(partSql);
        }

        public String toPartSql() {
            String partSql = stringBuilder.toString();
            if (PartType.CONDITION_SQL.equals(partType)) {
                return partSql.isEmpty() ? DEFAULT_CONDITION_SQL : partSql.replaceFirst("(?i)(^\\s*)and", " ");
            } else {
                return partSql;
            }
        }
    }

    public enum PartType {CONDITION_SQL, DATA_SQL}

    public Part newPart(String name, PartType scope) {
        Part part = new Part(name, scope);
        parts.add(part);
        return part;
    }

    public Query addValue(String paramName, Object value) {
        parameterSource.addValue(paramName, value);
        return this;
    }

    public Query setCondition(BiConsumer<Part, Query> conditionSqlBuilder) {
        originSql += " WHERE ${" + CONDITION + "}";
        Part part = new Part(CONDITION, PartType.CONDITION_SQL);
        conditionSqlBuilder.accept(part, this);
        parts.add(part);
        return this;
    }

    public Query setOrder(@Language("sql") String orderSql) {
        originSql += " ${" + ORDER + "}";
        Part part = new Part(ORDER, PartType.DATA_SQL);
        parts.add(part.append("ORDER BY").append(orderSql));
        return this;
    }

    public Query setPage(int page, int size) {
        originSql += " ${" + LIMIT + "}";
        Part part = new Part(LIMIT, PartType.DATA_SQL);
        parts.add(part.append("LIMIT :limitStartRow, :limitSize"));
        return addValue("limitStartRow", page).addValue("limitSize", size);
    }

    public <R> Page<R> getPageResult(Class<R> rClass) {
        String countSql = originSql;
        for (Part part : parts) {
            countSql = replaceAll(countSql, part.getName(), PartType.CONDITION_SQL.equals(part.getPartType()) ? part.toPartSql() : "");
        }
        countSql = "SELECT COUNT(*) FROM (" + countSql + ") as XXX";
        Long count = namedParameterJdbcTemplate.queryForObject(countSql, parameterSource, Long.class);
        if (count == null || count <= 0) {
            return Page.empty();
        }
        String dataSql = originSql;
        for (Part part : parts) {
            dataSql = replaceAll(dataSql, part.getName(), part.toPartSql());
        }
        List<R> data = namedParameterJdbcTemplate.query(dataSql, parameterSource, new BeanPropertyRowMapper<>(rClass));
        return new PageImpl<>(data, Pageable.unpaged(), count);
    }

    private String replaceAll(String sql, String name, String partSql) {
        return sql.replaceAll("\\$\\{" + name + "}", partSql);
    }
}
