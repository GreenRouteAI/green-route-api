package app.green.route.testutils;

import app.green.route.endpoint.rest.client.ApiClient;
import java.io.IOException;
import java.net.ServerSocket;

public class TestUtils {
  public static String VALID_TOKEN = "valid_token";

  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    client.setRequestInterceptor(
        httpRequestBuilder -> httpRequestBuilder.header("Authorization", "Bearer " + token));
    return client;
  }

  public static int anAvailablePort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
