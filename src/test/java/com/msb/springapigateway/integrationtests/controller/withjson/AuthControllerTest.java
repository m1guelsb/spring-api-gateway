package com.msb.springapigateway.integrationtests.controller.withjson;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msb.springapigateway.configs.TestConfigs;
import com.msb.springapigateway.data.vo.v1.SignUpDto;
import com.msb.springapigateway.data.vo.v1.SignedDto;
import com.msb.springapigateway.integrationtests.testcontainers.AbstractIntegrationTest;
import com.msb.springapigateway.integrationtests.vo.SignInDto;
import com.msb.springapigateway.models.enums.UserRole;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
public class AuthControllerTest extends AbstractIntegrationTest {

  private static SignedDto signedDto;

  @Test
  @Order(0)
  void testSignUp() throws JsonMappingException, JsonProcessingException {

    SignUpDto user = new SignUpDto("m1guelsb", "123", UserRole.ADMIN);

    signedDto = given()
        .basePath("/api/v1/auth/signup")
        .port(TestConfigs.SERVER_PORT)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(user)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(SignedDto.class);

    assertNotNull(signedDto.accessToken());
    assertNotNull(signedDto.refreshToken());

  }

  @Test
  @Order(1)
  void testSignin() throws JsonMappingException, JsonProcessingException {

    SignInDto user = new SignInDto("m1guelsb", "123");

    signedDto = given()
        .basePath("/api/v1/auth/signin")
        .port(TestConfigs.SERVER_PORT)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(user)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .as(SignedDto.class);

    assertNotNull(signedDto.accessToken());
    assertNotNull(signedDto.refreshToken());

  }
}
