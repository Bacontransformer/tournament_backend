package com.ybk.exception;

/**
 * 用户名已存在
 */
public class UsernameAlreadyExistedException extends BaseException{
    public UsernameAlreadyExistedException(){
    }

    public UsernameAlreadyExistedException(String msg){
        super(msg);
    }
}
