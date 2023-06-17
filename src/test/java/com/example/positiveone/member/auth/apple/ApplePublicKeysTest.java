package com.example.positiveone.member.auth.apple;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplePublicKeysTest {

    @Test
    @DisplayName("alg, kid 값을 통해 apple public key 반환")
    void getMatcheKey(){
        ApplePublicKey applePublicKey = new ApplePublicKey("kty", "kid", "use", "alg", "n", "e");

        ApplePublicKeys applePublicKeys = new ApplePublicKeys(List.of(applePublicKey));

        assertThat(applePublicKeys.getMatchesKey("alg", "kid"));
    }


    @Test
    @DisplayName("alg, kid 값이 일치하는 것이 없다면 exception")
    void getMatcheInvalidKey(){
        ApplePublicKey applePublicKey = new ApplePublicKey("kty", "kid", "use", "alg", "n", "e");

        ApplePublicKeys applePublicKeys = new ApplePublicKeys(List.of(applePublicKey));

        assertThatThrownBy(() -> applePublicKeys.getMatchesKey("invalid", "invalid"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}