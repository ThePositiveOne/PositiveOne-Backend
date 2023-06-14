package com.example.positiveone.global.filter;

import com.example.positiveone.global.security.authentication.CustomUserDetailService;
import com.example.positiveone.global.security.token.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtFilter extends BasicAuthenticationFilter {

    private final JWTProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;


    public JwtFilter(AuthenticationManager authenticationManager, JWTProvider jwtProvider, CustomUserDetailService customUserDetailService) {
        super(authenticationManager);
        this.jwtProvider = jwtProvider;
        this.customUserDetailService = customUserDetailService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            String email = jwtProvider.getPayload(authorizationHeader.substring(7));

            Authentication authentication = getAuthentication(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }


    private Authentication getAuthentication(String email){
        UserDetails userDetails = customUserDetailService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, null);
    }

}
