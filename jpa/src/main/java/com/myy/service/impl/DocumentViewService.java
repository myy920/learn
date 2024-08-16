package com.myy.service.impl;

import com.myy.dao.entity.DocumentEntity;
import com.myy.service.dto.DocCriteria;
import com.myy.util.page.PageResult;
import com.myy.util.page.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.intellij.lang.annotations.Language;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Slf4j
@Service
public class DocumentViewService {

    @Resource
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Language("sql")
    private static final String SQL = "select * from test_document where ${CONDITION}";

    public PageResult<DocumentEntity> pageDocument(DocCriteria criteria, Pageable pageable) {
        StringBuilder builder = new StringBuilder(SQL);
        MapSqlParameterSource param = new MapSqlParameterSource();
        if (CollectionUtils.isNotEmpty(criteria.getTitles())) {
            builder.append(" and title in (:titles) ");
            builder.append(" and title in (:titles) ");
            param.addValue("titles", criteria.getTitles());
        }
        if (StringUtils.isNotEmpty(criteria.getTitle())) {
            builder.append(" and title = :title ");
            param.addValue("title", criteria.getTitle());
        }
        if (StringUtils.isNotEmpty(criteria.getContentLike())) {
            builder.append(" and content like :content ");
            param.addValue("content", '%' + criteria.getContentLike() + '%');
        }
        if (StringUtils.isNotEmpty(criteria.getVersion())) {
            builder.append(" and version = :version ");
            param.addValue("version", criteria.getVersion());
        }
        if (Objects.nonNull(criteria.getIsLastVersion())) {
            builder.append(" and is_last_version = :isLastVersion ");
            param.addValue("isLastVersion", criteria.getIsLastVersion());
        }
        String sql = builder.toString();
        String countSql = "select count(*) from (" + sql + ") as XXX";
        Long total = namedParameterJdbcTemplate.queryForObject(countSql, param, Long.class);
        if (total == null || total == 0L) {
            return PageResult.empty();
        }
        builder.append(" limit :start, :size ");
        param.addValue("start", (pageable.getPageNumber() - 1) * pageable.getPageSize());
        param.addValue("size", pageable.getPageSize());
        String dataSql = builder.toString();
        List<DocumentEntity> data = namedParameterJdbcTemplate.query(dataSql, param, new BeanPropertyRowMapper<>(DocumentEntity.class));
        Page<?> page = new PageImpl<>(data, pageable, total);
        return PageResult.of(total, data);
    }

    public Page<DocumentEntity> pageDocument2(DocCriteria criteria, Pageable pageable) {
        @Language("sql") String sql = "select * from test_document where ${CONDITION}";
        return new Query0(sql).condition((condition, param) -> {
            StringBuilder condition1 = condition.apply("CONDITION1");
            StringBuilder condition2 = condition.apply("CONDITION2");
            if (CollectionUtils.isNotEmpty(criteria.getTitles())) {
                condition1.append(" and title in (:titles) ");
                condition2.append(" and title in (:titles) ");
                param.addValue("titles", criteria.getTitles());
            }
            if (StringUtils.isNotEmpty(criteria.getContentLike())) {
                condition1.append(" and content like :content ");
                condition2.append(" and content like :content ");
                param.addValue("content", '%' + criteria.getContentLike() + '%');
            }
            if (StringUtils.isNotEmpty(criteria.getVersion())) {
                condition1.append(" and version = :version ");
                condition2.append(" and version = :version ");
                param.addValue("version", criteria.getVersion());
            }
            if (Objects.nonNull(criteria.getIsLastVersion())) {
                condition1.append(" and is_last_version = :isLastVersion ");
                condition2.append(" and is_last_version = :isLastVersion ");
                param.addValue("isLastVersion", criteria.getIsLastVersion());
            }
            StringBuilder order = condition.apply("ORDER");
            Sort sort = pageable.getSort();
            Sort.Order so;
            if (Objects.nonNull(so = sort.getOrderFor("version"))) {
                order.append(" order by version ").append(so.isAscending() ? "ASC" : "DESC");
            }
            if (Objects.nonNull(so = sort.getOrderFor("createTime"))) {
                order.append(" order by  create_time ").append(so.isAscending() ? "ASC" : "DESC");
            }
        }).pageResult(pageable.getPageNumber(), pageable.getPageSize(), DocumentEntity.class);
    }

    public class Query0 {

        private String sql;

        private final Map<String, StringBuilder> conditionMap = new HashMap<>();

        private final MapSqlParameterSource parameterSource = new MapSqlParameterSource();

        public Query0(String sql) {
            this.sql = sql;
        }

        public Query0 condition(BiConsumer<Function<String, StringBuilder>, MapSqlParameterSource> buildSqlParam) {
            Function<String, StringBuilder> newCondition = name -> {
                StringBuilder builder = new StringBuilder();
                conditionMap.put(name, builder);
                return builder;
            };
            buildSqlParam.accept(newCondition, parameterSource);
            return this;
        }

        public <R> Page<R> pageResult(int page, int size, Class<R> tClass) {
            if (!conditionMap.isEmpty()) {
                conditionMap.forEach((key, value) -> sql = sql.replaceAll("\\$\\{" + key + "}", value.toString()));
            }
            String countSql = "select count(*) from (" + sql + ") as XXX";
            Long total = namedParameterJdbcTemplate.queryForObject(countSql, parameterSource, Long.class);
            if (total == null || total == 0L) {
                return Page.empty();
            }
            String dataSql = sql + " limit :rowNum, :fetchSize ";
            parameterSource.addValue("rowNum", (page - 1) * size);
            parameterSource.addValue("fetchSize", size);
            List<R> data = namedParameterJdbcTemplate.query(dataSql, parameterSource, new BeanPropertyRowMapper<>(tClass));
            return new PageImpl<>(data, Pageable.unpaged(), total);
        }
    }

    public Page<DocumentEntity> pageDocument3(DocCriteria criteria, Pageable pageable) {
        return new Query("select * from test_document").setCondition((part, query) -> {
            if (CollectionUtils.isNotEmpty(criteria.getTitles())) {
                part.and("title in (:titles)");
                query.addValue("titles", criteria.getTitles());
            }
            if (StringUtils.isNotEmpty(criteria.getContentLike())) {
                part.and("content like :content");
                query.addValue("content", '%' + criteria.getContentLike() + '%');
            }
            if (StringUtils.isNotEmpty(criteria.getVersion())) {
                part.and("version = :version");
                query.addValue("version", criteria.getVersion());
            }
            if (Objects.nonNull(criteria.getIsLastVersion())) {
                part.and("is_last_version = :isLastVersion");
                query.addValue("isLastVersion", criteria.getIsLastVersion());
            }
        }).setOrder("create_time desc").setPage(pageable.getPageNumber(), pageable.getPageSize()).getPageResult(DocumentEntity.class);
    }

    public Page<DocumentEntity> pageDocument4(DocCriteria criteria, Pageable pageable) {
        Query query = new Query("select * from test_document where ${CONDITION1} AND ${CONDITION2} ${ORDER}");
        Query.Part condition1 = query.newPart("CONDITION1", Query.PartType.CONDITION_SQL);
        Query.Part condition2 = query.newPart("CONDITION2", Query.PartType.CONDITION_SQL);
        Query.Part order = query.newPart("ORDER", Query.PartType.DATA_SQL);
        if (CollectionUtils.isNotEmpty(criteria.getTitles())) {
            condition1.and("title IN (:titles)");
            condition2.and("title IN (:titles)");
            query.addValue("titles", criteria.getTitles());
        }
        if (StringUtils.isNotEmpty(criteria.getContentLike())) {
            condition1.and("content LIKE :content");
            condition2.and("content LIKE :content");
            query.addValue("content", '%' + criteria.getContentLike() + '%');
        }
        if (StringUtils.isNotEmpty(criteria.getVersion())) {
            condition1.and("version = :version");
            condition2.and("version = :version");
            query.addValue("version", criteria.getVersion());
        }
        if (Objects.nonNull(criteria.getIsLastVersion())) {
            condition1.and("is_last_version = :isLastVersion");
            condition2.and("is_last_version = :isLastVersion");
            query.addValue("isLastVersion", criteria.getIsLastVersion());
        }
        order.append("ORDER BY").append(buildDocument4OrderSql(pageable));
        query.setPage(pageable.getPageNumber(), pageable.getPageSize());
        return query.getPageResult(DocumentEntity.class);
    }

    private String buildDocument4OrderSql(Pageable pageable) {
        Sort sort = pageable.getSort();
        Sort.Order sOrder;
        if (Objects.nonNull(sOrder = sort.getOrderFor("createTime"))) {
            return "create_time " + (sOrder.isAscending() ? "ASC" : "DESC");
        }
        if (Objects.nonNull(sOrder = sort.getOrderFor("updateTime"))) {
            return "update_time " + (sOrder.isAscending() ? "ASC" : "DESC");
        }
        return "create_time DESC";
    }

}
