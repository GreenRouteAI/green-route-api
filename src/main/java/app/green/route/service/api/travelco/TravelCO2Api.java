package app.green.route.service.api.travelco;

import static app.green.route.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.green.route.model.exception.ApiException;
import app.green.route.service.api.travelco.payload.AccommodationCarboneFootPrint;
import app.green.route.service.api.travelco.payload.AccommodationPayload;
import app.green.route.service.api.travelco.payload.TransportCarboneFootPrint;
import app.green.route.service.api.travelco.payload.TransportPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TravelCO2Api {
  private static final ObjectMapper mapper = new ObjectMapper();
  private static final String BEARER_PREFIX = "Bearer ";
  private final String apiKey;
  private final String baseUrl;
  private final HttpClient client;

  public TravelCO2Api(
      @Value("${travel.api.key}") String key,
      @Value("${travel.api.url}") String url,
      HttpClient httpClient) {
    apiKey = key;
    baseUrl = url;
    client = httpClient;
  }

  public TransportCarboneFootPrint evaluateTransport(TransportPayload payload) {
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(new URI(baseUrl + "/api/v1/transport"))
              .header("Authorization", BEARER_PREFIX + apiKey)
              .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      log.info("Foot print resp {}", response.body());
      return mapper.readValue(response.body(), TransportCarboneFootPrint.class);
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }

  public AccommodationCarboneFootPrint evaluateAccommodation(AccommodationPayload payload) {
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(new URI(baseUrl + "/api/v1/accommodation"))
              .header("Authorization", BEARER_PREFIX + apiKey)
              .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      log.info("Foot print resp {}", response.body());
      return mapper.readValue(response.body(), AccommodationCarboneFootPrint.class);
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }
}
