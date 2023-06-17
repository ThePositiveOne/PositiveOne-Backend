package com.example.positiveone.member.auth;

import com.example.positiveone.member.auth.apple.AppleClient;
import com.example.positiveone.member.auth.apple.ApplePublicKey;
import com.example.positiveone.member.auth.apple.ApplePublicKeys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AppleClientTest {

    @Autowired
    private AppleClient appleClient;

    @Test
    @DisplayName("apple auth keys 응답")
    void getPublicKeys(){
        ApplePublicKeys applePublicKeys = appleClient.getApplePublicKeys();
        List<ApplePublicKey> keys = applePublicKeys.getKeys();

        boolean isKeysNonNull = keys.stream()
                .allMatch(this::isAllNotNull);

        assertThat(isKeysNonNull).isTrue();
    }
    private boolean isAllNotNull(ApplePublicKey applePublicKey){
        return Objects.nonNull(applePublicKey.getKty()) && Objects.nonNull(applePublicKey.getKid()) &&
                Objects.nonNull(applePublicKey.getUse()) && Objects.nonNull(applePublicKey.getAlg()) &&
                Objects.nonNull(applePublicKey.getN()) && Objects.nonNull(applePublicKey.getE());
    }
    

}
