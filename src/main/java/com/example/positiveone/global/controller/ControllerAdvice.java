package com.example.positiveone.global.controller;

import com.example.positiveone.global.exception.baseException.BaseException;
import com.example.positiveone.global.exception.notFound.NotFoundException;
import com.example.positiveone.global.exception.unAuthorized.UnAuthorizedException;
import com.example.positiveone.global.response.ErrorResponse;
import com.example.positiveone.global.response.ResultResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public ResultResponse<ErrorResponse> handleBaseException(BaseException e){
        return ResultResponse.fail(ErrorResponse.of(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnAuthorizedException.class)
    public ResultResponse<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e){
        return ResultResponse.fail(ErrorResponse.of(e.getMessage()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResultResponse<ErrorResponse> handleNotFoundException(NotFoundException e){
        return ResultResponse.fail(ErrorResponse.of(e.getMessage()));
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResultResponse<ErrorResponse> handleBaseException(Exception e, HttpServletRequest request){
        log.error("UnhandledException: {} {} errMessage={}\n",
                request.getMethod(),
                request.getRequestURI(),
                e.getMessage()
        );
        return ResultResponse.fail(ErrorResponse.of(e.getMessage()));
    }
}
