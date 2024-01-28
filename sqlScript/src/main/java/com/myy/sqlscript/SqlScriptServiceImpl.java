package com.myy.sqlscript;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

@Service
public class SqlScriptServiceImpl implements SqlScriptService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Override
    public void exec(String path) {
        String sqlScript = findSqlByPath(path);
        doExec(sqlScript);
    }

    @Override
    public void exec(ScriptParam param) {
        String path = param.getPath();
        String sqlScript = findSqlByPath(path);
        Map<String, String> paramMap = param.getParamMap();
        if (paramMap != null) {
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                sqlScript = sqlScript.replace(entry.getKey(), entry.getValue());
            }
        }
        doExec(sqlScript);
    }

    private String findSqlByPath(String path) {
        try (InputStream in = SqlScriptServiceImpl.class.getClassLoader().getResourceAsStream(path)) {
            return read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String read(InputStream in) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doExec(String execSql) {
        System.out.println(execSql);
        jdbcTemplate.execute(execSql);
    }

}
