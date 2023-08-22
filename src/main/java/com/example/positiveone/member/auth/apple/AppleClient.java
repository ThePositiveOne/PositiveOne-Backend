package com.example.positiveone.member.auth.apple;

import com.example.positiveone.member.dto.req.AppleTokenReq;
import com.example.positiveone.member.dto.res.AppleTokenRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "apple-auth-keys", url = "https://appleid.apple.com/auth")
public interface AppleClient {

    @GetMapping("/keys")
    ApplePublicKeys getApplePublicKeys();

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    AppleTokenRes getToken(AppleTokenReq appleTokenReq);

}
