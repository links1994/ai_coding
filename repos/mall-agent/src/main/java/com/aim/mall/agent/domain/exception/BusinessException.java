package com.aim.mall.agent.domain.exception;

import com.aim.mall.common.api.IErrorCode;

/**
 * 业务异常
 */
public class BusinessException extends RuntimeException {

    private IErrorCode errorCode;
    
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public BusinessException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public BusinessException(IErrorCode errorCode, Throwable cause) {
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