package app.green.route.unit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

import app.green.route.service.api.weather.WeatherApi;
import app.green.route.service.api.weather.payload.WeatherResponse;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class WeatherApiTest {

  @Mock private HttpClient httpClient;

  @Test
  public void read_forecast_ok() throws InterruptedException, IOException {
    String apiKey = "api-key";
    String baseUrl = "http://localhost:8080";
    String location = "Paris";

    String responseBody = "{}";
    HttpResponse httpResponse = mock(HttpResponse.class);
    Mockito.when(httpResponse.body()).thenReturn(responseBody);

    Mockito.when(httpClient.send(any(HttpRequest.class), any())).thenReturn(httpResponse);

    WeatherApi api = new WeatherApi(apiKey, baseUrl, httpClient);

    WeatherResponse actual = api.getForecasts(location);

    assertNotNull(actual);
  }
}
