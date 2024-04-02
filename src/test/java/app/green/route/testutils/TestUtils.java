package app.green.route.testutils;

import app.green.route.endpoint.rest.client.ApiClient;
import java.io.IOException;
import java.net.ServerSocket;

public class TestUtils {
  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    return client;
  }

  public static int anAvailablePort(){
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
