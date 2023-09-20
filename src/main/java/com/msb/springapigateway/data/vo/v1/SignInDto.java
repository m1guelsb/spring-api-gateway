package com.msb.springapigateway.data.vo.v1;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignInDto(
    @NotNull @NotBlank String login,
    @NotNull @NotBlank String password) {
}
