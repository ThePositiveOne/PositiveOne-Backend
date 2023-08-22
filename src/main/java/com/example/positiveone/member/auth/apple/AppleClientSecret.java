package com.example.positiveone.member.auth.apple;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.security.PrivateKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class AppleClientSecret {

    @Value("${oauth.apple.key-id}")
    private String keyId;
    @Value("${oauth.apple.team-id}")
    private String teamId;
    @Value("${oauth.apple.client-id}")
    private String clientId;
    @Value("${oauth.apple.iss}")
    private String authUrl;
    @Value("${oauth.apple.key-path}")
    private String keyPath;

    public String createSecret() {
        Date expirationDate = Date.from(LocalDateTime.now().plusDays(1)
                .atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setHeaderParam("kid",keyId)
                .setHeaderParam("alg", "ES256")
                .setIssuer(teamId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience(authUrl)
                .setSubject(clientId)
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            ClassPathResource resource = new ClassPathResource(keyPath);
            String privateKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
            Reader pemReader = new StringReader(privateKey);
            PEMParser pemParser = new PEMParser(pemReader);
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKeyInfo object = (PrivateKeyInfo) pemParser.readObject();
            return converter.getPrivateKey(object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
