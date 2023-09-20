package com.msb.springapigateway.exceptions.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.msb.springapigateway.exceptions.ExceptionResponse;
import com.msb.springapigateway.exceptions.InvalidJwtAuthException;
import com.msb.springapigateway.exceptions.RequiredObjectIsNullException;
import com.msb.springapigateway.exceptions.ResourceNotFoundException;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(
      Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundExceptions(
      Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(RequiredObjectIsNullException.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(
      Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(InvalidJwtAuthException.class)
  public final ResponseEntity<ExceptionResponse> handleInvalidJwtAuthException(
      Exception ex, WebRequest request) {

    ExceptionResponse exceptionResponse = new ExceptionResponse(
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
  }

}