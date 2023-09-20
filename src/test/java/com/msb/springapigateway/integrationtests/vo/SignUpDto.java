package com.msb.springapigateway.integrationtests.vo;

import com.msb.springapigateway.models.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpDto(
    @NotNull @NotBlank String login,
    @NotNull @NotBlank String password,
    @NotNull @NotBlank UserRole role) {
}
