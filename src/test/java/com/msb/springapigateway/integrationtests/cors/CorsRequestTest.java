package com.msb.springapigateway.integrationtests.cors;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.msb.springapigateway.configs.TestConfigs;
import com.msb.springapigateway.integrationtests.testcontainers.AbstractIntegrationTest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
class CorsRequestTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;

  @Test
  @Order(1)
  void shouldAllowCors() throws JsonMappingException, JsonProcessingException {
    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WEB_DEV)
        .setBasePath("/api/v1/persons")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    given()
        .spec(specification)
        .when()
        .get()
        .then()
        .statusCode(200);

  }

  @Test
  @Order(2)
  void shouldDenyCors() throws JsonMappingException, JsonProcessingException {
    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, "http://invalidcors.com")
        .setBasePath("/api/v1/persons")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var content = given()
        .spec(specification)
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