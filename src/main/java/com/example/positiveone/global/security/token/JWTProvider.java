package com.example.positiveone.global.security.token;

import com.example.positiveone.global.exception.unAuthorized.ExpiredTokenException;
import com.example.positiveone.global.exception.unAuthorized.InvalidTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JWTProvider {
    private final String secretKey;
    private final long tokenValidTime;
    private final JwtParser jwtParser;

    public JWTProvider(@Value("${jwt.secret-key}") String secretKey,
                       @Value("${jwt.expire}") long tokenValidTime){
        this.secretKey = secretKey;
        this.tokenValidTime = tokenValidTime;
        this.jwtParser = Jwts.parser().setSigningKey(secretKey);
    }

    public String createToken(String subject){
        Date now = new Date();
        Date validity = new Date(now.getTime() + Duration.ofMinutes(tokenValidTime).toMillis());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getPayload(String token){
        try{
            return jwtParser.parseClaimsJws(token).getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (JwtException e) {
            throw new InvalidTokenException();
        }
    }

}
