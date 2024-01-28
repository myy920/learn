package com.myy.sqlscript;

import java.util.HashMap;
import java.util.Map;

public class ScriptParam {

    private String path;

    private Map<String, String> paramMap;

    public ScriptParam(String path) {
        this.path = path;
    }

    public ScriptParam(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

    public void addParam(String key, String value) {
        if (paramMap == null) {
            paramMap = new HashMap<>();
        }
        paramMap.put(key, value);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map<String, String> paramMap) {
        this.paramMap = paramMap;
    }

}
