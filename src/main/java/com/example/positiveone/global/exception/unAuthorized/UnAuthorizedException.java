package com.example.positiveone.global.exception.unAuthorized;

import com.example.positiveone.global.exception.PositiveOneException;
import com.example.positiveone.global.response.ErrorCode;

public class UnAuthorizedException extends PositiveOneException {

    public UnAuthorizedException(ErrorCode errorCode){
        super(errorCode.getMessage());
    }
}
