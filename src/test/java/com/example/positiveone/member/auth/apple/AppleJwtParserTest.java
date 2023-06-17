package com.example.positiveone.member.auth.apple;

import com.example.positiveone.global.exception.unAuthorized.ExpiredTokenException;
import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.security.*;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AppleJwtParserTest {

    AppleJwtParser appleJwtParser = new AppleJwtParser();

    @Test
    @DisplayName("identity token으로 헤더 파싱")
    void parseHeaders() throws NoSuchAlgorithmException {
        Date now = new Date();
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        String identityToken = Jwts.builder()
                .setHeaderParam("kid", "YuyXoY")
                .claim("id", "1234")
                .setIssuer("iss")
                .setIssuedAt(now)
                .setAudience("aud")
                .setExpiration(new Date(now.getTime()+ 60 *60 *24))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();


        Map<String, String> headers = appleJwtParser.parserHeaders(identityToken);


        assertThat(headers.get("kid")).isEqualTo("YuyXoY");
        assertThat(headers.get("alg")).isEqualTo("RS256");
    }


    @Test
    @DisplayName("올바르지 않은 identity token exception")
    void parseHeadersWithInvalidToken(){
        assertThatThrownBy(() -> appleJwtParser.parserHeaders("invalidToken"))
                .isInstanceOf(InvalidTokenException.class);
    }


    @Test
    @DisplayName("identity에서 claims 추출")
    void parseIdentityTokenAndGetClaims() throws NoSuchAlgorithmException {
        Date now = new Date();
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String identityToken = Jwts.builder()
                .setHeaderParam("kid", "YuyXoY")
                .claim("email", "1234@gmail.com")
                .setIssuer("iss")
                .setIssuedAt(now)
                .setAudience("aud")
                .setExpiration(new Date(now.getTime()+ 60 *60 *24))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();


        Claims claims = appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey);


        assertThat(claims.get("email", String.class)).isEqualTo("1234@gmail.com");
    }


    @Test
    @DisplayName("expired identity token excpetion")
    void parseExpiredTokenException() throws NoSuchAlgorithmException {
        Date now = new Date();
        KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String identityToken = Jwts.builder()
                .setHeaderParam("kid", "YuyXoY")
                .claim("email", "1234@gmail.com")
                .setIssuer("iss")
                .setIssuedAt(now)
                .setAudience("aud")
                .setExpiration(new Date(now.getTime() -1))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

        assertThatThrownBy(() -> appleJwtParser.parsePublicKeyAndGetClaims(identityToken, publicKey))
                .isInstanceOf(ExpiredTokenException.class);
    }



    @Test
    @DisplayName("invalid public key exception")
    void parseInvalidPublicKeyException() throws NoSuchAlgorithmException {
        Date now = new Date();
        PrivateKey privateKey = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPrivate();
        PublicKey differPublicKey = KeyPairGenerator.getInstance("RSA").generateKeyPair().getPublic();
        String identityToken = Jwts.builder()
                .setHeaderParam("kid", "YuyXoY")
                .claim("email", "1234@gmail.com")
                .setIssuer("iss")
                .setIssuedAt(now)
                .setAudience("aud")
                .setExpiration(new Date(now.getTime() -1))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();


        assertThatThrownBy(() -> appleJwtParser.parsePublicKeyAndGetClaims(identityToken, differPublicKey))
                .isInstanceOf(InvalidTokenException.class);
    }
}