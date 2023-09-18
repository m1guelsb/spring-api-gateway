package com.msb.springapigateway.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.msb.springapigateway.configs.TestConfigs;
import com.msb.springapigateway.integrationtests.testcontainers.AbstractIntegrationTest;
import com.msb.springapigateway.integrationtests.vo.PersonVO;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
@Testcontainers
public class PersonControllerJsonTest extends AbstractIntegrationTest {

  private static RequestSpecification specification;
  private static ObjectMapper objectMapper;

  private static PersonVO person;

  @BeforeAll
  public static void setup() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    person = new PersonVO();
  }

  @Test
  @Order(1)
  void testCreate() throws JsonMappingException, JsonProcessingException {
    mockPerson();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WEB_DEV)
        .setBasePath("/api/v1/persons")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var content = given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .body(person)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
    person = persistedPerson;

    assertNotNull(persistedPerson);

    assertNotNull(persistedPerson.getId());
    assertNotNull(persistedPerson.getFirstName());
    assertNotNull(persistedPerson.getLastName());
    assertNotNull(persistedPerson.getAddress());
    assertNotNull(persistedPerson.getGender());

    assertTrue(persistedPerson.getId() > 0);

    assertEquals("Richard", persistedPerson.getFirstName());
    assertEquals("Stallman", persistedPerson.getLastName());
    assertEquals("New York City, New York, US", persistedPerson.getAddress());
    assertEquals("Male", persistedPerson.getGender());
  }

  @Test
  @Order(2)
  void testFindById() throws JsonMappingException, JsonProcessingException {
    mockPerson();

    specification = new RequestSpecBuilder()
        .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_WEB_DEV)
        .setBasePath("/api/v1/persons")
        .setPort(TestConfigs.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    var content = given().spec(specification)
        .contentType(TestConfigs.CONTENT_TYPE_JSON)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    PersonVO persistedPerson = objectMapper.readValue(content, PersonVO.class);
    person = persistedPerson;

    assertNotNull(persistedPerson);

    assertNotNull(persistedPerson.getId());
    assertNotNull(persistedPerson.getFirstName());
    assertNotNull(persistedPerson.getLastName());
    assertNotNull(persistedPerson.getAddress());
    assertNotNull(persistedPerson.getGender());

    assertTrue(persistedPerson.getId() > 0);

    assertEquals("Richard", persistedPerson.getFirstName());
    assertEquals("Stallman", persistedPerson.getLastName());
    assertEquals("New York City, New York, US", persistedPerson.getAddress());
    assertEquals("Male", persistedPerson.getGender());
  }

  private void mockPerson() {
    person.setFirstName("Richard");
    person.setLastName("Stallman");
    person.setAddress("New York City, New York, US");
    person.setGender("Male");
  }

}