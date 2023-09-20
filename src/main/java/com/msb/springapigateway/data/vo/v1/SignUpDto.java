package com.msb.springapigateway.data.vo.v1;

import com.msb.springapigateway.models.enums.UserRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SignUpDto(
                @NotNull @NotBlank String login,
                @NotNull @NotBlank String password,
                @NotNull @NotBlank UserRole role) {
}
