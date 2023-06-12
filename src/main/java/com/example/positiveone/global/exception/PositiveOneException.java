package com.example.positiveone.global.exception;

import lombok.Getter;

@Getter
public class PositiveOneException extends RuntimeException{
    private final String message;

    public PositiveOneException(String message) {
        this.message = message;
    }

}
