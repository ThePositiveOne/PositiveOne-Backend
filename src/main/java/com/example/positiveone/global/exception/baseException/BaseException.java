package com.example.positiveone.global.exception.baseException;

import com.example.positiveone.global.exception.PositiveOneException;
import com.example.positiveone.global.response.ErrorCode;

public class BaseException extends PositiveOneException {

    public BaseException(ErrorCode errorCode){
        super(errorCode.getMessage());
    }
}
