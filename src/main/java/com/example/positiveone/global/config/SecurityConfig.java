package com.example.positiveone.global.config;

import com.example.positiveone.global.filter.JwtErrorFilter;
import com.example.positiveone.global.filter.JwtFilter;
import com.example.positiveone.global.security.authentication.CustomUserDetailService;
import com.example.positiveone.global.security.token.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JWTProvider jwtProvider;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtErrorFilter(), JwtFilter.class)
                .addFilter(new JwtFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class))
                        , jwtProvider, customUserDetailService))
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/board/all/**").permitAll()
                .anyRequest().authenticated();
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
