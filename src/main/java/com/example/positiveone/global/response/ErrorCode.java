package com.example.positiveone.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_BOARD("게시글을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER("사용자를 찾을 수 없습니다."),
    NOT_FOUND_HEART("좋아요를 취소할 수 없습니다."),
    ALREADY_HEART_PUSH("좋아요를 이미 눌렀습니다."),

    PLATFORM_INVALID("유효하지 않은 로그인 플랫폼입니다."),

    TOKEN_EXPIRED("유효기간이 만료된 토큰입니다."),
    TOKEN_INVALID("토큰 값이 올바르지 않습니다."),

    APPLE_CLAIMS_INCORRECT("apple claims 값이 올바르지 않습니다."),
    APPLE_OAUTH_ENCRYPTION_ERROR("apple oauth 통신 암호화 중 문제가 발생했습니다."),
    APPLE_IDENTITY_TOKEN_EXPIRED("apple identity token 유효기간이 만료됐습니다."),
    APPLE_IDENTITY_TOKEN_VALUE_INCORRECT("apple identity token 값이 올바르지 않습니다."),
    APPLE_PUBLIC_KEY_ERROR("apple public key 생성에 문제가 발생했습니다."),
    APPLE_IDENTITY_TOKEN_INCORRECT("apple identity token 형식이 올바르지 않습니다."),
    APPLE_JWT_INCORRECT("apple jwt 값이 올바르지 않습니다.");

    private final String message;

}
