package com.example.positiveone.member.auth.apple;

import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import com.example.positiveone.member.dto.res.OAuthMemberRes;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.security.*;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class AppleOAuthUserProviderTest {

    @Autowired
    private AppleOAuthUserProvider appleOAuthUserProvider;

    @MockBean
    private AppleClient appleClient;
    @MockBean
    private PublicKeyGenerator publicKeyGenerator;
    @MockBean
    private AppleClaimsValidator appleClaimsValidator;


    @Test
    @DisplayName("apple login 시 login id 반환")
    void getAppleId() throws NoSuchAlgorithmException {
        String sub = "12345";
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
                .setSubject(sub)
                .setExpiration(new Date(now.getTime()+ 60 *60 *24))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

        when(appleClient.getApplePublicKeys()).thenReturn(mock(ApplePublicKeys.class));
        when(publicKeyGenerator.generatePublicKey(any(), any())).thenReturn(publicKey);
        when(appleClaimsValidator.isValid(any())).thenReturn(true);


        OAuthMemberRes oAuthMemberRes = appleOAuthUserProvider.getAppleMember(identityToken);


        assertThat(oAuthMemberRes.getLoginId()).isEqualTo(sub);
        assertThat(oAuthMemberRes.getEmail()).isEqualTo("1234@gmail.com");
    }


    @Test
    @DisplayName("invalid claims exception")
    void invalidClaims() throws NoSuchAlgorithmException {
        String sub = "12345";
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
                .setSubject(sub)
                .setExpiration(new Date(now.getTime()+ 60 *60 *24))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();

        when(appleClient.getApplePublicKeys()).thenReturn(mock(ApplePublicKeys.class));
        when(publicKeyGenerator.generatePublicKey(any(), any())).thenReturn(publicKey);
        when(appleClaimsValidator.isValid(any())).thenReturn(false);


        assertThatThrownBy(() -> appleOAuthUserProvider.getAppleMember(identityToken))
                .isInstanceOf(InvalidTokenException.class);

    }

}