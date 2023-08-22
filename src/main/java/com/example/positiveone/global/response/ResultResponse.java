package com.example.positiveone.global.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) //null 제외
public class ResultResponse<T> {

    private boolean success;
    private T data;
    private String errorMessage;


    private ResultResponse(T data) {
        this.data = data;
    }


    public static <T> ResultResponse<T> success() {
        return ResultResponse.<T>builder().success(true).build();
    }

    public static <T> ResultResponse<T> success(T data) {
        return ResultResponse.<T>builder().success(true).data(data).build();
    }

    public static <T> ResultResponse<T> fail(ErrorResponse errorResponse) {
        return ResultResponse.<T>builder().success(false).errorMessage(errorResponse.getMessage()).build();
    }

}
