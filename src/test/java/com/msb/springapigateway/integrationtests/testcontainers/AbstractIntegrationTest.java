package com.msb.springapigateway.integrationtests.testcontainers;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractIntegrationTest {

  @ServiceConnection
  private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0");

  static {
    mySQLContainer
        .withUrlParam("serverTimezone", "UTC")
        .withConnectTimeoutSeconds(10000)
        .withReuse(true)
        .start();
  }
}