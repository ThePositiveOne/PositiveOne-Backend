package com.example.positiveone.member.auth.apple;

import com.example.positiveone.global.response.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplePublicKeys {

    private List<ApplePublicKey> keys;

    //- Verify the nonce for the authentication
    public ApplePublicKey getMatchesKey(String alg, String kid){
        return this.keys
                .stream()
                .filter(k -> k.getAlg().equals(alg) && k.getKid().equals(kid))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.APPLE_JWT_INCORRECT.getMessage()));
    }
}
