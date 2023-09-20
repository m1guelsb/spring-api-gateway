package com.msb.springapigateway.integrationtests.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInDto(
    @NotNull @NotBlank String login,
    @NotNull @NotBlank String password) {
}
