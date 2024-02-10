package com.agao.exception.user;

import com.agao.exception.CommonException;
import com.agao.exception.IExceptionEnum;

/**
 * 用户模块异常
 *
 * @author Agao
 * @date 2024/2/10 23:03
 */
public class UserException extends CommonException {
    public UserException(String message) {
        super(message);
    }

    public UserException(Integer code, String message) {
        super(code, message);
    }

    public UserException(IExceptionEnum iExceptionEnum) {
        super(iExceptionEnum);
    }
}
