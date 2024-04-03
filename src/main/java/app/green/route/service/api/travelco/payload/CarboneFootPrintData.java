package app.green.route.service.api.travelco.payload;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.USE_DEFAULTS;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class CarboneFootPrintData {
  private final String JSON_PROPERTY_TYPE = "type";
  private String type;
  private final String JSON_PROPERTY_TITLE = "title";
  private String title;
  private final String JSON_PROPERTY_DISTANCE = "distance";
  private double distance;
  private final String JSON_PROPERTY_PEOPLE = "people";
  private int people;
  private final String JSON_PROPERTY_WAYS = "ways";
  private int ways;
  private final String JSON_PROPERTY_CO2E = "co2e";
  private double co2e;
  private final String JSON_PROPERTY_CO2E_PP = "co2e_pp";
  private double co2ePP;
  private final String JSON_PROPERTY_VEHICLE = "vehicle";
  private Vehicle vehicle;

  @JsonProperty(JSON_PROPERTY_TYPE)
  @JsonInclude(value = USE_DEFAULTS)
  public String getType() {
    return type;
  }

  @JsonProperty(JSON_PROPERTY_TITLE)
  @JsonInclude(value = USE_DEFAULTS)
  public String getTitle() {
    return title;
  }

  @JsonProperty(JSON_PROPERTY_DISTANCE)
  @JsonInclude(value = USE_DEFAULTS)
  public double getDistance() {
    return distance;
  }

  @JsonProperty(JSON_PROPERTY_PEOPLE)
  @JsonInclude(value = USE_DEFAULTS)
  public int getPeople() {
    return people;
  }

  @JsonProperty(JSON_PROPERTY_WAYS)
  @JsonInclude(value = USE_DEFAULTS)
  public int getWays() {
    return ways;
  }

  @JsonProperty(JSON_PROPERTY_CO2E)
  @JsonInclude(value = USE_DEFAULTS)
  public double getCo2e() {
    return co2e;
  }

  @JsonProperty(JSON_PROPERTY_CO2E_PP)
  @JsonInclude(value = USE_DEFAULTS)
  public double getCo2ePP() {
    return co2ePP;
  }

  @JsonProperty(JSON_PROPERTY_VEHICLE)
  @JsonInclude(value = USE_DEFAULTS)
  public Vehicle getVehicle() {
    return vehicle;
  }

  class Vehicle {
    String type;
    String title;
    Fuel fuel;

    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(value = USE_DEFAULTS)
    public String getType() {
      return type;
    }

    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(value = USE_DEFAULTS)
    public String getTitle() {
      return title;
    }

    @JsonProperty("fuel")
    @JsonInclude(value = USE_DEFAULTS)
    public Fuel getFuel() {
      return fuel;
    }
  }

  class Fuel {
    String type;
    String title;

    @JsonProperty(JSON_PROPERTY_TYPE)
    @JsonInclude(value = USE_DEFAULTS)
    public String getType() {
      return type;
    }

    @JsonProperty(JSON_PROPERTY_TITLE)
    @JsonInclude(value = USE_DEFAULTS)
    public String getTitle() {
      return title;
    }
  }
}
