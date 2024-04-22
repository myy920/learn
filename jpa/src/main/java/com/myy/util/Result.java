package com.myy.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    private static final Integer CODE_ERROR = 0;

    private static final Integer CODE_OK = 1;

    private static final String MSG_ERROR = "error";

    public static final String MSG_OK = "ok";

    private Integer code;

    private String msg;

    private T data;

    public static <T> Result<T> of(Integer code, String msg, T data) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> error(String msg, T data) {
        return of(CODE_ERROR, msg, data);
    }

    public static <T> Result<T> ok(String msg, T data) {
        return of(CODE_OK, msg, data);
    }

    public static <T> Result<T> errorMsg(String msg) {
        return of(CODE_ERROR, msg, null);
    }

    public static <T> Result<T> okMsg(String msg) {
        return of(CODE_OK, msg, null);
    }

    public static <T> Result<T> error(T data) {
        return of(CODE_ERROR, MSG_ERROR, data);
    }

    public static <T> Result<T> ok(T data) {
        return of(CODE_OK, MSG_OK, data);
    }

    public static Result<Void> ok(Void data) {
        return of(CODE_OK, MSG_OK, data);
    }

    public static <T> Result<T> error() {
        return of(CODE_ERROR, MSG_ERROR, null);
    }

    public static <T> Result<T> ok() {
        return of(CODE_OK, MSG_OK, null);
    }

}
