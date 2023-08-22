package com.example.positiveone.member.service;

import com.example.positiveone.member.auth.apple.AppleClientSecret;
import com.example.positiveone.member.auth.apple.AppleOAuthUserProvider;
import com.example.positiveone.member.auth.apple.AppleToken;
import com.example.positiveone.member.domain.Member;
import com.example.positiveone.member.dto.req.AppleLoginInfoReq;
import com.example.positiveone.member.dto.res.AppleTokenRes;
import com.example.positiveone.member.dto.res.LoginTokenRes;
import com.example.positiveone.member.dto.res.OAuthMemberRes;
import com.example.positiveone.member.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.*;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private AppleOAuthUserProvider appleOAuthUserProvider;
    @MockBean
    private AppleToken appleToken;
    @MockBean
    private AppleClientSecret appleClientSecret;

    private String identityToken;


    @BeforeEach
    void setUp() throws NoSuchAlgorithmException {
        String sub = "12345";
        Date now = new Date();
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        identityToken = Jwts.builder()
                .setHeaderParam("kid", "YuyXoY")
                .claim("email", "1234@gmail.com")
                .setIssuer("iss")
                .setIssuedAt(now)
                .setAudience("aud")
                .setSubject(sub)
                .setExpiration(new Date(now.getTime()+ 60 *60 *24))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }


    @Test
    @DisplayName("등록된 회원 apple login 성공 시 token 반환")
    void getTokenWithSignInMember() {

        //given
        AppleLoginInfoReq appleLoginInfoReq = AppleLoginInfoReq.builder()
                .identityToken(identityToken)
                .build();

        Member member = Member.builder()
                .email("1234@gmail.com")
                .build();

        when(appleOAuthUserProvider.getAppleMember(any()))
                .thenReturn(mock(OAuthMemberRes.class));
        when(memberRepository.findMemberByLoginTypeAndLoginId(any(), any()))
                .thenReturn(java.util.Optional.ofNullable(member));


        //when
       LoginTokenRes loginTokenRes = loginService.appleLogin(appleLoginInfoReq);


       //then
        assertInstanceOf(String.class, loginTokenRes.getToken());
    }



    @Test
    @DisplayName("등록되지 않은 회원 apple login 성공 시 token 반환")
    void getTokenWithSignInNoMember() {

        //given
        String authCode= "auth_code";
        AppleLoginInfoReq appleLoginInfoReq = AppleLoginInfoReq.builder()
                .identityToken(identityToken)
                .authorization_code(authCode)
                .build();

        Member member = Member.builder()
                .email("aa123@gmail.com")
                .build();
        String token = "aaa";

        when(appleOAuthUserProvider.getAppleMember(any()))
                .thenReturn(mock(OAuthMemberRes.class));
        when(memberRepository.save(any())).thenReturn(member);

        when(appleClientSecret.getPrivateKey()).thenReturn(mock(PrivateKey.class));
        when(appleClientSecret.createSecret()).thenReturn(token);
        when(appleToken.getAppleToken(authCode, token))
                .thenReturn(mock(AppleTokenRes.class));


        //when
        LoginTokenRes loginTokenRes = loginService.appleLogin(appleLoginInfoReq);


        //then
        assertInstanceOf(String.class, loginTokenRes.getToken());
    }

}