package com.example.positiveone.global.exception.unAuthorized;


import com.example.positiveone.global.response.ErrorCode;

public class ExpiredTokenException extends UnAuthorizedException {
    public ExpiredTokenException(){
        super (ErrorCode.TOKEN_EXPIRED);
    }
    public ExpiredTokenException(ErrorCode errorCode){
        super (errorCode);
    }

}