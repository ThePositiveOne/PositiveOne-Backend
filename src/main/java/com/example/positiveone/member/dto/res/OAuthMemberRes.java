package com.example.positiveone.member.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OAuthMemberRes {

    //사용자 고유 식별값
    private String loginId;
    private String email;
}
