package com.example.positiveone.member.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppleTokenRes {
    private String refresh_token;
    private String access_token;
    private String token_type;
}
