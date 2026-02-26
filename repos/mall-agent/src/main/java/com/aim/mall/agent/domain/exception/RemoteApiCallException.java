package com.aim.mall.agent.domain.exception;

import com.aim.mall.common.api.IErrorCode;

/**
 * 远程API调用异常
 */
public class RemoteApiCallException extends RuntimeException {

    private IErrorCode errorCode;
    
    public RemoteApiCallException(String message) {
        super(message);
    }
    
    public RemoteApiCallException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public RemoteApiCallException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public RemoteApiCallException(IErrorCode errorCode, Throwable cause) {
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