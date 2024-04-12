package app.green.route.service.api.weather;

import static app.green.route.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.green.route.model.exception.ApiException;
import app.green.route.service.api.weather.payload.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherApi {
  private static final ObjectMapper mapper = new ObjectMapper();
  private final String apiKey;
  private final String baseUrl;
  private final HttpClient client;

  public WeatherApi(
      @Value("${weather.api.key}") String key,
      @Value("${weather.api.url}") String url,
      HttpClient httpClient) {
    apiKey = key;
    baseUrl = url;
    client = httpClient;
  }

  public WeatherResponse getForecasts(String location) {
    try {
      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(
                  new URI(
                      baseUrl
                          + "/v1/forecast.json"
                          + "?key="
                          + URLEncoder.encode(apiKey, StandardCharsets.UTF_8)
                          + "&q="
                          + URLEncoder.encode(location, StandardCharsets.UTF_8)
                          + "&days=3"
                          + "&aqi=yes"
                          + "&alerts=yes"))
              .GET()
              .build();
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
      log.info("Weather resp {}", response.body());
      return mapper.readValue(response.body(), WeatherResponse.class);
    } catch (URISyntaxException | IOException | InterruptedException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }
}
