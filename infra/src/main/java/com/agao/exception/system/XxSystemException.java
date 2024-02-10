package com.agao.exception.system;

import com.agao.exception.CommonException;
import com.agao.exception.IExceptionEnum;

/**
 * 系统级别异常
 * @author Agao
 * @date 2024/2/10 22:58
 */
public class XxSystemException extends CommonException {
    public XxSystemException(String message) {
        super(message);
    }

    public XxSystemException(Integer code, String message) {
        super(code, message);
    }

    public XxSystemException(IExceptionEnum iExceptionEnum) {
        super(iExceptionEnum);
    }
}
