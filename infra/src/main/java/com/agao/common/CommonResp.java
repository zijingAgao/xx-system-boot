package com.agao.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * 公共返回对象
 *
 * @author Agao
 * @date 2024/2/6 10:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResp<T> {
    private Integer code;
    private String msg;
    private T data;
    private Pagination pagination;

    public CommonResp(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> CommonResp<T> success() {
        return new CommonResp<>(200, "success", null, null);
    }

    public static <T> CommonResp<T> success(T data) {
        return new CommonResp<>(200, "success", data, null);
    }

    public static <T> CommonResp<T> success(T data, Pagination pagination) {
        return new CommonResp<>(200, "success", data, pagination);
    }

    public static <T> CommonResp<T> success(T data, Page page) {
        return success(data, new Pagination(page.getNumber(), page.getTotalPages(), page.getTotalElements()));
    }

    public static <T> CommonResp<T> error() {
        return new CommonResp<>(400, "error", null, null);
    }

    public static <T> CommonResp<T> error(Integer code) {
        return new CommonResp<>(code, "error", null, null);
    }

    public static <T> CommonResp<T> error(String msg) {
        return new CommonResp<>(400, msg, null, null);
    }

    public static <T> CommonResp<T> error(T e) {
        return new CommonResp<>(400, "", e, null);
    }

    public static <T> CommonResp<T> error(Integer code, String msg) {
        return new CommonResp<>(code, msg, null, null);
    }

    public static <T> CommonResp<T> error(Exception e) {
        return CommonResp.error(500, e.getMessage());
    }

}
