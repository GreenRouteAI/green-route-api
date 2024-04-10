package app.green.route.service.api.travelco.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonPropertyOrder({
  TransportCarboneFootPrint.JSON_PROPERTY_DISTANCE,
  TransportCarboneFootPrint.JSON_PROPERTY_PEOPLE,
  TransportCarboneFootPrint.JSON_PROPERTY_WAYS,
  TransportCarboneFootPrint.JSON_PROPERTY_CO2E,
  TransportCarboneFootPrint.JSON_PROPERTY_CO2E_PP,
  TransportCarboneFootPrint.JSON_PROPERTY_VEHICLE,
  TransportCarboneFootPrint.JSON_PROPERTY_TYPE,
  TransportCarboneFootPrint.JSON_PROPERTY_TITLE
})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class TransportCarboneFootPrint {
  public static final String JSON_PROPERTY_TYPE = "type";
  public static final String JSON_PROPERTY_TITLE = "title";
  public static final String JSON_PROPERTY_DISTANCE = "distance";
  public static final String JSON_PROPERTY_PEOPLE = "people";
  public static final String JSON_PROPERTY_WAYS = "ways";
  public static final String JSON_PROPERTY_CO2E = "co2e";
  public static final String JSON_PROPERTY_CO2E_PP = "co2e_pp";
  public static final String JSON_PROPERTY_VEHICLE = "vehicle";

  private String type;
  private String title;
  private int distance;
  private int people;
  private int ways;
  private double co2e;
  private double co2ePP;
  private Vehicle vehicle;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Vehicle {
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;

    @JsonProperty("vehicle_count")
    private int vehicleCount;

    private Fuel fuel;
    private String consumption;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Fuel {
    @JsonProperty(JSON_PROPERTY_TYPE)
    private String type;

    @JsonProperty(JSON_PROPERTY_TITLE)
    private String title;
  }
}
