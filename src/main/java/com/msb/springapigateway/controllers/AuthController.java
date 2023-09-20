package com.msb.springapigateway.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msb.springapigateway.config.auth.TokenProvider;
import com.msb.springapigateway.data.vo.v1.SignInDto;
import com.msb.springapigateway.data.vo.v1.SignUpDto;
import com.msb.springapigateway.data.vo.v1.SignedDto;
import com.msb.springapigateway.models.User;
import com.msb.springapigateway.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private AuthService service;
  @Autowired
  private TokenProvider tokenService;

  @Operation(summary = "Sign up user")
  @PostMapping("/signup")
  public ResponseEntity<SignedDto> register(@RequestBody @Valid SignUpDto data) {
    var userDetails = service.signUp(data);

    var accessToken = tokenService.generateAccessToken((User) userDetails);
    var refreshToken = tokenService.generateRefreshToken((User) userDetails);

    return ResponseEntity.ok(new SignedDto(accessToken, refreshToken, userDetails.getUsername()));

  }

  @Operation(summary = "Authenticate user with token")
  @PostMapping("/signin")
  public ResponseEntity<SignedDto> login(@RequestBody @Valid SignInDto data) {
    var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());

    var authUser = authenticationManager.authenticate(usernamePassword);

    var accessToken = tokenService.generateAccessToken((User) authUser.getPrincipal());
    var refreshToken = tokenService.generateRefreshToken((User) authUser.getPrincipal());

    return ResponseEntity.ok(new SignedDto(accessToken, refreshToken, authUser.getName()));
  }

}