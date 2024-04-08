package app.green.route.testutils;

import static org.mockito.Mockito.when;

import app.green.route.endpoint.rest.client.ApiClient;
import app.green.route.endpoint.rest.model.User;
import app.green.route.service.api.firebase.FUser;
import app.green.route.service.api.firebase.FirebaseService;
import java.io.IOException;
import java.net.ServerSocket;

public class TestUtils {
  public static String VALID_TOKEN = "valid_token";
  public static String BAD_TOKEN = "bad_token";
  private static String USER1_ID = "user1_id";
  private static String USER1_AUTHENTICATION_ID = "user1_authentication_id";

  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    client.setRequestInterceptor(
        httpRequestBuilder -> httpRequestBuilder.header("Authorization", "Bearer " + token));
    return client;
  }

  public static void setFirebaseService(FirebaseService firebaseService) {
    when(firebaseService.getUserByBearer(VALID_TOKEN))
        .thenReturn(new FUser(USER1_AUTHENTICATION_ID, "user1@email.com"));
  }

  public static User expected() {
    return new User()
        .id(USER1_ID)
        .birthDate(null)
        .firstName("nyhasina")
        .lastName("vagno")
        .username("nyhasina14")
        .email("user1@email.com")
        .photoId("photo_id")
        .authenticationId(USER1_AUTHENTICATION_ID);
  }

  public static User toCreate() {
    return new User()
        .id("user2_id")
        .birthDate(null)
        .firstName("user2")
        .lastName("user2_lastname")
        .username("user214")
        .email("user2@email.com")
        .photoId("photo2_id")
        .authenticationId("user2_auth_id");
  }

  public static int anAvailablePort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
