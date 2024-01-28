package com.myy.sqlscript;

public interface SqlScriptService {

    void exec(String path);

    void exec(ScriptParam param);

}
