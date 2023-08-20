package com.example.positiveone.member.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppleTokenReq {
    private String client_id;
    private String client_secret;
    private String code;
    private String grant_type;
    private String refresh_token;
}
