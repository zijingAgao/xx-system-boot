package com.agao.exception;

/**
 * 3位数为系统异常返回码
 * 响应码规则:各个模块固定4位返回码
 * <p>
 * 1***：用户模块
 *
 * @author Agao
 * @date 2024/2/10 15:11
 */

public interface IExceptionEnum {
    Integer getCode();

    void setCode(Integer code);

    String getMsg();

    void setMsg(String msg);
}
