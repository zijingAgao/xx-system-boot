package com.agao.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 *
 * @author Agao
 * @date 2024/2/6 10:08
 */
@NoArgsConstructor
@AllArgsConstructor
public class CommonResp<T> {
    private Integer code;
    private String msg;
    private T data;


    public static <T> CommonResp<T> success() {
        return new CommonResp<>(200, "success", null);
    }

    public static <T> CommonResp<T> success(T data) {
        return new CommonResp<>(200, "success", data);
    }

    public static <T> CommonResp<T> error() {
        return new CommonResp<>(400, "error", null);
    }

    public static <T> CommonResp<T> error(Integer code) {
        return new CommonResp<>(code, "error", null);
    }

    public static <T> CommonResp<T> error(String msg) {
        return new CommonResp<>(400, msg, null);
    }

    public static <T> CommonResp<T> error(T e) {
        return new CommonResp<>(400, "", e);
    }

    public static <T> CommonResp<T> error(Integer code, String msg) {
        return new CommonResp<>(code, msg, null);
    }

}
