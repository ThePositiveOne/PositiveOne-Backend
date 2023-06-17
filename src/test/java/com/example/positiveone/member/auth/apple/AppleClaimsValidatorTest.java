package com.example.positiveone.member.auth.apple;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class AppleClaimsValidatorTest {

    private static final String ISS = "iss";
    private static final String CLIENT_ID = "aud";

    private final AppleClaimsValidator appleClaimsValidator = new AppleClaimsValidator(ISS, CLIENT_ID);

    @Test
    @DisplayName("claims 검증")
    void isValid(){

        Claims claims = Jwts.claims()
                .setIssuer(ISS)
                .setAudience(CLIENT_ID);


        assertThat(appleClaimsValidator.isValid(claims)).isTrue();
    }


    @Test
    @DisplayName("invalid claims exception")
    void isInvalid(){

        Claims claims = Jwts.claims()
                .setIssuer("invalid")
                .setAudience("invalid");


        assertThat(appleClaimsValidator.isValid(claims)).isFalse();
    }
}