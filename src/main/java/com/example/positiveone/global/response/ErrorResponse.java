package com.example.positiveone.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private final String message;

    public static ErrorResponse of(String message) {
        return new ErrorResponse(message);
    }
}
