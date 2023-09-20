package com.msb.springapigateway.data.vo.v1;

public record SignedDto(
        String accessToken,
        String refreshToken,
        String username) {
}