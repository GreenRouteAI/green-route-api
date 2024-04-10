package app.green.route.integration;

import static app.green.route.testutils.TestUtils.BAD_TOKEN;
import static app.green.route.testutils.TestUtils.VALID_TOKEN;
import static app.green.route.testutils.TestUtils.anAvailablePort;
import static app.green.route.testutils.TestUtils.setFirebaseService;
import static app.green.route.testutils.TestUtils.toCreate;
import static app.green.route.testutils.TestUtils.user1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.green.route.AbstractContextInitializer;
import app.green.route.endpoint.rest.api.UserApi;
import app.green.route.endpoint.rest.client.ApiClient;
import app.green.route.endpoint.rest.client.ApiException;
import app.green.route.endpoint.rest.model.User;
import app.green.route.service.api.firebase.FirebaseService;
import app.green.route.testutils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = UserControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class UserControllerIT {
  @MockBean private FirebaseService firebaseService;

  @BeforeEach
  void setUp() {
    setFirebaseService(firebaseService);
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @Test
  void user_read_self_by_id_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    UserApi api = new UserApi(client);

    User actual = api.getUserById(user1().getId());

    assertEquals(user1(), actual);
  }

  // Uncomment when self matcher is correctly set
  /**
   * @Test void other_user_read_other_by_id_ko() throws ApiException { ApiClient client =
   * anApiClient(VALID_TOKEN); UserApi api = new UserApi(client);
   *
   * <p>assertThrows(ApiException.class, () -> api.getUserById(toCreate().getId())); }*
   */
  @Test
  void read_user_by_id_ko() throws ApiException {
    ApiClient client = anApiClient(BAD_TOKEN);
    UserApi api = new UserApi(client);

    assertThrows(ApiException.class, () -> api.getUserById(toCreate().getId()));
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
