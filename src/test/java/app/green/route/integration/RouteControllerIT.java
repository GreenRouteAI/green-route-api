package app.green.route.integration;

import static app.green.route.testutils.TestUtils.VALID_TOKEN;
import static app.green.route.testutils.TestUtils.anAvailablePort;
import static app.green.route.testutils.TestUtils.setFirebaseService;
import static app.green.route.testutils.TestUtils.setGeminiConf;
import static app.green.route.testutils.TestUtils.travelDescription;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.green.route.AbstractContextInitializer;
import app.green.route.endpoint.rest.api.RouteApi;
import app.green.route.endpoint.rest.client.ApiClient;
import app.green.route.endpoint.rest.client.ApiException;
import app.green.route.endpoint.rest.model.Itinerary;
import app.green.route.service.api.firebase.FirebaseService;
import app.green.route.service.api.gemini.conf.GeminiConf;
import app.green.route.service.api.travelco.TravelCO2Api;
import app.green.route.service.api.travelco.payload.CarboneFootPrintData;
import app.green.route.service.api.travelco.payload.TravelCO2Payload;
import app.green.route.testutils.TestUtils;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ContextConfiguration(initializers = RouteControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
public class RouteControllerIT {
  @MockBean private GeminiConf geminiConf;
  @MockBean private TravelCO2Api travelCO2Api;
  @MockBean private FirebaseService firebaseService;

  @BeforeEach
  void setUp() {
    setGeminiConf(geminiConf);
    setFirebaseService(firebaseService);
    when(travelCO2Api.evaluateCO2e(any(TravelCO2Payload.class)))
        .thenReturn(
            CarboneFootPrintData.builder()
                .co2e(10.3)
                .co2ePP(2.36)
                .distance(5000)
                .people(2)
                .vehicle(new CarboneFootPrintData.Vehicle())
                .build());
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  @Test
  void generate_itineraries_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    RouteApi api = new RouteApi(client);

    Itinerary actual = api.generateItineraries(travelDescription());

    assertEquals("Hi", actual.getTravelDescription());
    assertEquals(new BigDecimal("10.3"), actual.getTransport().getCo2e());
    assertEquals(new BigDecimal("2.36"), actual.getTransport().getCo2ePp());
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
