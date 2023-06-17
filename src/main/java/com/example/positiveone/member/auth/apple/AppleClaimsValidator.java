package com.example.positiveone.member.auth.apple;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppleClaimsValidator {

    private String iss;
    private String clientId;


    public AppleClaimsValidator(
            @Value("${oauth.apple.iss}") String iss,
            @Value("${oauth.apple.client-id}") String clientId
    ){
        this.iss = iss;
        this.clientId = clientId;
    }


    //- Verify that the iss field contains `https://appleid.apple.com`
    //- Verify that the aud field is the developer’s client_id
    public boolean isValid(Claims claims){
        return claims.getIssuer().contains(iss) &&
                claims.getAudience().equals(clientId);
    }
}
