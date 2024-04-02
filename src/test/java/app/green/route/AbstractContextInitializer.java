package app.green.route;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class AbstractContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final String FLYWAY_TESTDATA_PATH = "classpath:/db/testdata";

  private PostgreSQLContainer<?> postgresContainer() {
    return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
        .withDatabaseName("dummy")
        .withUsername("dummy")
        .withPassword("dummy");
  }

  @Override
  public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
    TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
        applicationContext,
        "server.port=8080",
        "spring.datasource.url=" + postgresContainer().getJdbcUrl(),
        "spring.datasource.username=" + postgresContainer().getUsername(),
        "spring.datasource.password=" + postgresContainer().getPassword(),
        "spring.flyway.locations=classpath:/db/migration," + FLYWAY_TESTDATA_PATH);
  }
}
