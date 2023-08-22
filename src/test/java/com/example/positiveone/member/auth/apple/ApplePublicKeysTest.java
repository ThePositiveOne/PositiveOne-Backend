package com.example.positiveone.member.auth.apple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ApplePublicKeysTest {

    @Test
    @DisplayName("alg, kid 값을 통해 apple public key 반환")
    void getMatcheKey(){
        ApplePublicKey applePublicKey = new ApplePublicKey("kty", "kid", "use", "alg", "n", "e");

        ApplePublicKeys applePublicKeys = new ApplePublicKeys(List.of(applePublicKey));

        assertEquals(applePublicKeys.getMatchesKey("alg", "kid"), applePublicKey);
    }


    @Test
    @DisplayName("alg, kid 값이 일치하는 것이 없다면 exception")
    void getMatcheInvalidKey(){
        ApplePublicKey applePublicKey = new ApplePublicKey("kty", "kid", "use", "alg", "n", "e");

        ApplePublicKeys applePublicKeys = new ApplePublicKeys(List.of(applePublicKey));

        assertThrows(IllegalArgumentException.class,
                () -> applePublicKeys.getMatchesKey("invalid", "invalid"));
    }
}