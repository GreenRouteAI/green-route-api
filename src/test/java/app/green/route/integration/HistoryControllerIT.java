package app.green.route.integration;

import static app.green.route.testutils.TestUtils.VALID_TOKEN;
import static app.green.route.testutils.TestUtils.anAvailablePort;
import static app.green.route.testutils.TestUtils.setFileStorageService;
import static app.green.route.testutils.TestUtils.setFirebaseService;
import static app.green.route.testutils.TestUtils.setGeminiConf;
import static app.green.route.testutils.TestUtils.setTravelApi;
import static app.green.route.testutils.TestUtils.travelDescription;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.green.route.AbstractContextInitializer;
import app.green.route.endpoint.rest.api.RouteApi;
import app.green.route.endpoint.rest.api.UserApi;
import app.green.route.endpoint.rest.client.ApiClient;
import app.green.route.endpoint.rest.client.ApiException;
import app.green.route.endpoint.rest.model.Itinerary;
import app.green.route.service.api.firebase.FirebaseService;
import app.green.route.service.api.gemini.conf.GeminiConf;
import app.green.route.service.api.travelco.TravelCO2Api;
import app.green.route.service.file.FileStorageService;
import app.green.route.testutils.TestUtils;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = HistoryControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class HistoryControllerIT {
  @MockBean private FirebaseService firebaseService;
  @MockBean private TravelCO2Api travelCO2Api;
  @MockBean private GeminiConf geminiConf;
  @MockBean private FileStorageService fileStorageService;

  @BeforeEach
  void setUp() throws IOException {
    setFirebaseService(firebaseService);
    setGeminiConf(geminiConf);
    setTravelApi(travelCO2Api);
    setFileStorageService(fileStorageService);
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @Test
  void read_activities_history_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    UserApi api = new UserApi(client);
    RouteApi routeApi = new RouteApi(client);

    Itinerary itinerary = routeApi.generateItineraries(travelDescription());

    List<Itinerary> actual = api.getActivities();

    assertTrue(actual.contains(itinerary));
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
