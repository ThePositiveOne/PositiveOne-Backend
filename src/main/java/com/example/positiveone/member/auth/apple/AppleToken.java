package com.example.positiveone.member.auth.apple;

import com.example.positiveone.member.dto.req.AppleTokenReq;
import com.example.positiveone.member.dto.res.AppleTokenRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppleToken {

    private final AppleClient appleClient;

    @Value("${oauth.apple.client-id}")
    private String clientId;
    @Value("${oauth.apple.grant-type}")
    private String grantType;


    public AppleTokenRes getAppleToken(String code, String clientSecret){
        AppleTokenReq appleTokenReq = AppleTokenReq.builder()
                .client_id(clientId)
                .client_secret(clientSecret)
                .code(code)
                .grant_type(grantType)
                .build();
        return appleClient.getToken(appleTokenReq);
    }
}
