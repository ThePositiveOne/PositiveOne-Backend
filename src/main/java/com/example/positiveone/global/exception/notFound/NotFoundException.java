package com.example.positiveone.global.exception.notFound;

import com.example.positiveone.global.exception.PositiveOneException;
import com.example.positiveone.global.response.ErrorCode;

public class NotFoundException extends PositiveOneException {

    public NotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage());
    }
}
