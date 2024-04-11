package app.green.route.integration;

import static app.green.route.testutils.TestUtils.VALID_TOKEN;
import static app.green.route.testutils.TestUtils.anAvailablePort;
import static app.green.route.testutils.TestUtils.setFileStorageService;
import static app.green.route.testutils.TestUtils.setFirebaseService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import app.green.route.AbstractContextInitializer;
import app.green.route.endpoint.rest.api.RouteApi;
import app.green.route.endpoint.rest.client.ApiClient;
import app.green.route.endpoint.rest.client.ApiException;
import app.green.route.endpoint.rest.model.InlineObject;
import app.green.route.endpoint.rest.model.Weather;
import app.green.route.service.api.firebase.FirebaseService;
import app.green.route.service.api.weather.WeatherApi;
import app.green.route.service.api.weather.payload.WeatherResponse;
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
@ContextConfiguration(initializers = WeatherControllerIT.ContextInitializer.class)
@AutoConfigureMockMvc
class WeatherControllerIT {
  @MockBean private WeatherApi weatherApi;
  @MockBean private FirebaseService firebaseService;
  @MockBean private FileStorageService fileStorageService;

  @BeforeEach
  void setUp() throws IOException {
    setFirebaseService(firebaseService);
    setFileStorageService(fileStorageService);
    when(weatherApi.getForecasts(any()))
        .thenReturn(
            WeatherResponse.builder()
                .location(
                    WeatherResponse.Location.builder()
                        .country("Madagascar")
                        .name("Toliara")
                        .region("Toliara")
                        .build())
                .forecast(
                    WeatherResponse.Forecast.builder().forecastDay(List.of(forecastDay())).build())
                .build());
  }

  @Test
  void read_weather_ok() throws ApiException {
    ApiClient client = anApiClient(VALID_TOKEN);
    RouteApi api = new RouteApi(client);

    Weather actual = api.getForecast(origDest());

    assertNotNull(actual);
    assertNotNull(actual.getOrigin());
    assertNotNull(actual.getDestination());
  }

  public WeatherResponse.ForecastDay forecastDay() {
    return WeatherResponse.ForecastDay.builder()
        .date("2024-04-09")
        .day(
            WeatherResponse.Day.builder()
                .maxTempC("12.3")
                .minTempC("7.3")
                .condition(
                    WeatherResponse.Condition.builder()
                        .icon("//cdn.weatherapi.com/weather/64x64/day/176.png")
                        .text("Patchy rain nearby")
                        .build())
                .build())
        .build();
  }

  public InlineObject origDest() {
    return new InlineObject().origin("Madagascar").destination("Paris");
  }

  private ApiClient anApiClient(String token) {
    return TestUtils.anApiClient(token, ContextInitializer.SERVER_PORT);
  }

  static class ContextInitializer extends AbstractContextInitializer {
    public static final int SERVER_PORT = anAvailablePort();

    @Override
    public int getServerPort() {
      return SERVER_PORT;
    }
  }
}
