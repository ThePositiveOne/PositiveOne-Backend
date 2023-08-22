package com.example.positiveone.member.service;

import com.example.positiveone.global.security.token.JWTProvider;
import com.example.positiveone.member.auth.apple.AppleClientSecret;
import com.example.positiveone.member.auth.apple.AppleToken;
import com.example.positiveone.member.domain.Member;
import com.example.positiveone.member.dto.res.OAuthMemberRes;
import com.example.positiveone.member.auth.apple.AppleOAuthUserProvider;
import com.example.positiveone.member.domain.LoginType;
import com.example.positiveone.member.dto.req.AppleLoginInfoReq;
import com.example.positiveone.member.dto.res.LoginTokenRes;
import com.example.positiveone.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final JWTProvider jwtProvider;
    private final AppleOAuthUserProvider appleOAuthUserProvider;
    private final AppleClientSecret appleClientSecret;
    private final AppleToken appleToken;

    private final String[] happyName = {"건강한 긍정이", "감사한 긍정이", "느긋한 긍정이", "뿌듯한 긍정이",
            "따뜻한 긍정이", "즐거운 긍정이", "신난 긍정이", "귀여운 긍정이", "친절한 긍정이", "온화한 긍정이",
            "고요한 긍정이", "힘찬 긍정이", "사랑스러운 긍정이", "싱그러운 긍정이", "생기있는 긍정이", "기쁜 긍정이",
            "활력 넘치는 긍정이", "화사한 긍정이", "따사로운 긍정이", "만족스런 긍정이", "상쾌한 긍정이", "자유로운 긍정이"};
    private final Random rand =new Random();

    public LoginTokenRes appleLogin(AppleLoginInfoReq token){
        OAuthMemberRes appleMember = appleOAuthUserProvider.getAppleMember(token.getIdentityToken());
        return generateToken(LoginType.APPLE, appleMember.getEmail(), appleMember.getLoginId(), token.getAuthorization_code());
    }

    private LoginTokenRes generateToken(LoginType loginType, String email, String loginId, String auth_code){
        Member findMember = memberRepository.findMemberByLoginTypeAndLoginId(loginType, loginId)
                .orElseGet(() -> memberRepository.save(Member.builder()
                                                .name(getRandomName())
                                                .email(email)
                                                .loginType(loginType)
                                                .loginId(loginId)
                                                .refreshToken(getAppleTokenWithNewMember(auth_code))
                                                .build()));
        String token = issueToken(findMember);
        return new LoginTokenRes(token);
    }

    public String getRandomName(){
        return happyName[rand.nextInt(happyName.length)];
    }

    public String issueToken(Member member){
        return jwtProvider.createToken(member.getEmail());
    }

    public String getAppleTokenWithNewMember(String code){
        String clientSecret = appleClientSecret.createSecret();
        return appleToken.getAppleToken(code, clientSecret).getRefresh_token();
    }
}
