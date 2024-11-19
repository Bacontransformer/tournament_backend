package com.ybk.exception;

/**
 * 无权限错误
 */
public class NoPermissionException extends BaseException{
    public NoPermissionException(String msg){
        super(msg);
    }
}
