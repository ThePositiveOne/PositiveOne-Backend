package com.example.positiveone.member.controller;


import com.example.positiveone.global.response.ResultResponse;
import com.example.positiveone.member.dto.req.AppleLoginInfoReq;
import com.example.positiveone.member.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final LoginService loginService;


    @PostMapping("/apple")
    public ResultResponse<?> getBoard(@RequestBody AppleLoginInfoReq authTokenDTO){
        return ResultResponse.success(loginService.appleLogin(authTokenDTO));
    }

}
