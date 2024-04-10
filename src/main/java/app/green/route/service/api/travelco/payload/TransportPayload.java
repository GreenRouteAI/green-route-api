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
public class TransportPayload {
  private double distance;
  private int people;
  private Vehicle vehicle;

  @AllArgsConstructor
  @Data
  @ToString
  public static class Vehicle {
    String type;
    String description;
    Fuel fuel;
  }

  @AllArgsConstructor
  @Data
  @ToString
  public static class Fuel {
    String type;
    String description;
  }
}
