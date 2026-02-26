package com.aim.mall.agent.domain.exception;

import com.aim.mall.common.api.IErrorCode;

/**
 * 参数校验异常
 */
public class MethodArgumentValidationException extends RuntimeException {

    private IErrorCode errorCode;
    
    public MethodArgumentValidationException(String message) {
        super(message);
    }
    
    public MethodArgumentValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MethodArgumentValidationException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public MethodArgumentValidationException(IErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
    
    public IErrorCode getErrorCode() {
        return errorCode;
    }
    
    public void setErrorCode(IErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}