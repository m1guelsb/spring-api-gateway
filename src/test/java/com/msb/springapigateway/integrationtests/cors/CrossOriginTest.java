package com.msb.springapigateway.integrationtests.cors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.msb.springapigateway.configs.TestConfigs;
import com.msb.springapigateway.data.vo.v1.SignUpDto;
import com.msb.springapigateway.data.vo.v1.SignedDto;
import com.msb.springapigateway.integrationtests.testcontainers.AbstractIntegrationTest;
import com.msb.springapigateway.models.enums.UserRole;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
class CrossOriginTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;

  @Test
  @Order(0)
  public void should_authorize() throws JsonMappingException, JsonProcessingException {

    SignUpDto user = new SignUpDto("m1guelsb-cors-test", "123", UserRole.ADMIN);

    var accessToken = given()
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
        .as(SignedDto.class).accessToken();

    assertNotNull(accessToken);

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_AUTHORIZATION, "Bearer " + accessToken)
        .setBasePath("/api/v1/persons")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();
  }

  @Test
  @Order(1)
  void should_allow_cors() throws JsonMappingException, JsonProcessingException {

    given()
        .spec(specification)
        .header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WEB_DEV)
        .when()
        .get()
        .then()
        .statusCode(200);
  }

  @Test
  @Order(2)
  void should_deny_cors() throws JsonMappingException, JsonProcessingException {

    var content = given()
        .spec(specification)
        .header(TestConfigs.HEADER_PARAM_ORIGIN, "http://invalidorigin.com")
        .when()
        .get()
        .then()
        .statusCode(403)
        .extract()
        .body()
        .asString();

    assertEquals("Invalid CORS request", content);
  }
}