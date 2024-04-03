package app.green.route.service.api.travelco.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class TravelCO2Payload {
  private double distance;
  private int people;
  private Vehicle vehicle;

  class Vehicle {
    String type;
    String description;
    Fuel fuel;
  }

  class Fuel {
    String type;
    String description;
  }
}