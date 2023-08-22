package com.example.positiveone.global.security.token;

import com.example.positiveone.global.exception.unAuthorized.ExpiredTokenException;
import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import io.jsonwebtoken.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;



class JWTProviderTest {

    private final String secretKey = "positiveOneKey";
    private final long tokenValidTime = 30;
    private final JwtParser jwtParser  = Jwts.parser().setSigningKey(secretKey);

    private final JWTProvider jwtProvider = new JWTProvider(secretKey, tokenValidTime);


    @Test
    @DisplayName("jwt 생성")
    void createToken(){
        //given
        String email = "asd123@gmail.com";

        //when
        String newToken = jwtProvider.createToken(email);
        String parseEmail = jwtParser.parseClaimsJws(newToken).getBody().getSubject();

        //then
        assertEquals(email, parseEmail);
    }


    @Test
    @DisplayName("jwt에서 email 추출")
    void getEmailByJWt(){

        //given
        String email = "asd123@gmail.com";
        String newToken = jwtProvider.createToken(email);


        //when
        String parseEmail = jwtProvider.getPayload(newToken);


        //then
        assertEquals(email, parseEmail);
    }


    @Test
    @DisplayName("만료된 jwt")
    void expiredJWT(){

        //given
        String subject = "asd123@gmail.com";

        //when
        Date now = new Date();
        String newToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();


        //then
        assertThrows(ExpiredTokenException.class,
                     () -> jwtProvider.getPayload(newToken));

    }



    @Test
    @DisplayName("유효하지 않은 jwt")
    void invalidJWT(){

        //given
        String subject = "asd123@gmail.com";

        //when
        Date now = new Date();
        String newToken = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(now)
                .signWith(SignatureAlgorithm.HS256, "jkkl")
                .compact();


        //then
        assertThrows(InvalidTokenException.class,
                () -> jwtProvider.getPayload(newToken));

    }

}