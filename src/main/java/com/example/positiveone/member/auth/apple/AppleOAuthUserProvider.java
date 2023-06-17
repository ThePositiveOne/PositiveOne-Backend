package com.example.positiveone.member.auth.apple;

import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import com.example.positiveone.global.response.ErrorCode;
import com.example.positiveone.member.dto.res.OAuthMemberRes;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AppleOAuthUserProvider {

    private final AppleJwtParser appleJwtParser;
    private final AppleClient appleClient;
    private final PublicKeyGenerator publicKeyGenerator;
    private final AppleClaimsValidator appleClaimsValidator;

    public OAuthMemberRes getAppleMember(String identityToken){
        Map<String, String> headers = appleJwtParser.parserHeaders(identityToken);
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();

        PublicKey publicKey = publicKeyGenerator.generatePublicKey(headers, applePublicKeys);

        Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);
        validateClaims(claims);
        return new OAuthMemberRes(claims.getSubject(), claims.get("email", String.class));
    }

    private void validateClaims(Claims claims){
        if(!appleClaimsValidator.isValid(claims)){
            throw new InvalidTokenException(ErrorCode.APPLE_CLAIMS_INCORRECT);
        }
    }
}
