package com.example.positiveone.member.controller;

import com.example.positiveone.global.response.ResultResponse;
import com.example.positiveone.member.dto.req.AppleLoginInfoReq;
import com.example.positiveone.member.dto.res.LoginTokenRes;
import com.example.positiveone.member.service.LoginService;
import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

    @MockBean
    private LoginService loginService;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUP() {
        RestAssured.port = port;
    }


    @Test
    @DisplayName("apple login success")
    void loginAppleSuccess(){

        AppleLoginInfoReq appleLoginInfoReq = new AppleLoginInfoReq("id_token", "auth_code");
        LoginTokenRes oauthRes = new LoginTokenRes("123456789");

        when(loginService.appleLogin(any())).thenReturn(oauthRes);
        ResultResponse<?> res = RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(appleLoginInfoReq)
                .when().post("/login/apple")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(ResultResponse.class);


        String token = JsonPath.parse(res.getData()).read("$.token");
        assertEquals(token, oauthRes.getToken());
    }
}