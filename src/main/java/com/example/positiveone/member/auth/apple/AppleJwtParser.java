package com.example.positiveone.member.auth.apple;

import com.example.positiveone.global.exception.unAuthorized.ExpiredTokenException;
import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import com.example.positiveone.global.response.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import java.security.PublicKey;
import java.util.Map;

@Component
public class AppleJwtParser {

    private static final String IDENTITY_TOKEN_DELIMITER = "\\.";
    private static final int INDEX=0;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Map<String, String> parserHeaders(String identityToken){
        try {
            String encodedHeader = identityToken.split(IDENTITY_TOKEN_DELIMITER)[INDEX];
            String decodedHeader = new String(Base64Utils.decodeFromUrlSafeString(encodedHeader));
            return objectMapper.readValue(decodedHeader, Map.class);
        } catch (JsonProcessingException | ArrayIndexOutOfBoundsException e){
            throw new InvalidTokenException(ErrorCode.APPLE_IDENTITY_TOKEN_INCORRECT);
        }
    }


    //- Verify that the time is earlier than the exp value of the token
    public Claims parsePublicKeyAndGetClaims(String idToken, PublicKey publicKey){
        try{
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(idToken)
                    .getBody();
        } catch (ExpiredJwtException e){
            throw new ExpiredTokenException(ErrorCode.APPLE_IDENTITY_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e){
            throw new InvalidTokenException(ErrorCode.APPLE_IDENTITY_TOKEN_VALUE_INCORRECT);
        }
    }

}
