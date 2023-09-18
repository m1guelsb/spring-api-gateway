package com.msb.springapigateway.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.msb.springapigateway.configs.TestConfigs;
import com.msb.springapigateway.integrationtests.testcontainers.AbstractIntegrationTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

  @Test
  void shouldDisplaySwaggerUiPage() {
    var content = given()
        .basePath("/swagger-ui/index.html")
        .port(TestConfigs.SERVER_PORT)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();
    assertTrue(content.contains("Swagger UI"));
  }

}