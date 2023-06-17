package com.example.positiveone.global.exception.unAuthorized;


import com.example.positiveone.global.exception.notFound.NotFoundException;
import com.example.positiveone.global.response.ErrorCode;

public class InvalidTokenException extends UnAuthorizedException {
    public InvalidTokenException(){
        super (ErrorCode.TOKEN_INVALID);
    }

    public InvalidTokenException(ErrorCode errorCode){
        super(errorCode);
    }
}