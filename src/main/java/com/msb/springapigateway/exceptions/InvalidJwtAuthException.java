package com.msb.springapigateway.exceptions;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthException extends AuthenticationException {

  public InvalidJwtAuthException(String ex) {
    super(ex);
  }
}
