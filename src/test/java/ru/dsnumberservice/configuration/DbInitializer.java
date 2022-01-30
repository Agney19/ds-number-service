package ru.dsnumberservice.configuration;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.InternetProtocol;
import org.testcontainers.containers.PostgreSQLContainer;

public class DbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  int fixedPort = 0;

  public DbInitializer(int fixedPort) {
    super();
    this.fixedPort = fixedPort;
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:12.6");
    if (fixedPort != 0) {
      postgres.setPortBindings(java.util.List.of(String.format("%d:%d/%s", fixedPort, PostgreSQLContainer.POSTGRESQL_PORT, InternetProtocol.TCP)));
    }
    postgres.start();
    TestPropertyValues.of(
      "spring.datasource.url=" + postgres.getJdbcUrl(),
      "spring.datasource.username=" + postgres.getUsername(),
      "spring.datasource.password=" + postgres.getPassword()
    ).applyTo(applicationContext);
  }
}
