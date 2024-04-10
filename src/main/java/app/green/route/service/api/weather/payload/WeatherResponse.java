package app.green.route.service.api.weather.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class WeatherResponse {
  private Location location;
  private Forecast forecast;

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @ToString
  @EqualsAndHashCode
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Location {
    private String name;
    private String region;
    private String country;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @ToString
  @EqualsAndHashCode
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Forecast {
    @JsonProperty("forecastday")
    private List<ForecastDay> forecastDay;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @ToString
  @EqualsAndHashCode
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class ForecastDay {
    private String date;
    private Day day;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @ToString
  @EqualsAndHashCode
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Day {
    @JsonProperty("maxtemp_c")
    private String maxTempC;

    @JsonProperty("mintemp_c")
    private String minTempC;

    private Condition condition;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  @ToString
  @EqualsAndHashCode
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Condition {
    private String text;
    private String icon;
  }
}
