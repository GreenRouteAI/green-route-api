package app.green.route.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import app.green.route.service.api.travelco.TravelCO2Api;
import app.green.route.service.api.travelco.payload.AccommodationCarboneFootPrint;
import app.green.route.service.api.travelco.payload.AccommodationPayload;
import app.green.route.service.api.travelco.payload.TransportCarboneFootPrint;
import app.green.route.service.api.travelco.payload.TransportPayload;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TravelCO2ApiTest {
  private static final String apiKey = "api-key";
  private static final String baseUrl = "http://localhost:8080";

  @Mock private HttpClient httpClient;

  @BeforeEach
  void setUp() throws IOException, InterruptedException {
    String responseBody = "{}";
    HttpResponse httpResponse = mock(HttpResponse.class);
    Mockito.when(httpResponse.body()).thenReturn(responseBody);

    Mockito.when(httpClient.send(any(HttpRequest.class), any())).thenReturn(httpResponse);
  }

  @Test
  public void evaluate_transport_ok() {
    var payload = new TransportPayload();

    TravelCO2Api api = new TravelCO2Api(apiKey, baseUrl, httpClient);

    TransportCarboneFootPrint actual = api.evaluateTransport(payload);

    assertNotNull(actual);
  }

  @Test
  public void evaluate_accommodation_ok() {
    var payload = new AccommodationPayload();

    TravelCO2Api api = new TravelCO2Api(apiKey, baseUrl, httpClient);

    AccommodationCarboneFootPrint actual = api.evaluateAccommodation(payload);

    assertNotNull(actual);
  }
}
