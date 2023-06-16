package com.example.positiveone.global.filter;

import com.example.positiveone.global.exception.unAuthorized.ExpiredTokenException;
import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import com.example.positiveone.global.response.ErrorCode;
import com.example.positiveone.global.response.ErrorResponse;
import com.example.positiveone.global.response.ResultResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtErrorFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            setErrorResponse(response, ErrorCode.TOKEN_EXPIRED);
        } catch (InvalidTokenException e){
            setErrorResponse(response, ErrorCode.TOKEN_INVALID);
        }
    }

    private void setErrorResponse(
            HttpServletResponse response,
            ErrorCode errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");
        ResultResponse<ErrorResponse> resultResponse = ResultResponse.fail(ErrorResponse.of(errorCode.getMessage()));
        try{
            response.getWriter().println(objectMapper.writeValueAsString(resultResponse));
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
