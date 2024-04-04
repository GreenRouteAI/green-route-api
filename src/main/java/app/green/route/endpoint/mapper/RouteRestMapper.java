package app.green.route.endpoint.mapper;

import app.green.route.endpoint.rest.model.Fuel;
import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.endpoint.rest.model.Vehicle;
import app.green.route.service.api.travelco.payload.TravelCO2Payload;
import org.springframework.stereotype.Component;

@Component
public class RouteRestMapper {
  public TravelCO2Payload toDomainTravelPayload(TravelDescription rest) {
    return TravelCO2Payload.builder()
        .distance(rest.getDistance())
        .people(rest.getPeople())
        .vehicle(toDomainVehicle(rest.getVehicle()))
        .build();
  }

  private TravelCO2Payload.Vehicle toDomainVehicle(Vehicle vehicle) {
    return new TravelCO2Payload.Vehicle(
        vehicleType(vehicle.getType()),
        vehicleType(vehicle.getType()),
        new TravelCO2Payload.Fuel(
            fuelType(vehicle.getFuel().getType()), fuelType(vehicle.getFuel().getType())));
  }

  private String vehicleType(Vehicle.TypeEnum type) {
    return switch (type) {
      case BICYCLE -> "bicycle";
      case SMALL_CAR -> "car-small";
      case LARGE_CAR -> "car-large";
      case BUS -> "bus";
      case MINIVAN -> "minivan";
      case MOTOR_HOME -> "motorhome";
      case FLIGHT_REGULAR_ECONOMY -> "flight-regular-economy";
      case FLIGHT_CHARTER_ECONOMY -> "flight-charter-economy";
      case HIKING -> "hiking";
      case KAYAK -> "kayak";
      case TRAM -> "tram";
      case SUBWAY -> "subway";
      case FERRY -> "ferry";
      case TRAIN -> "train";
      case WALKING -> "walking";
    };
  }

  private String fuelType(Fuel.TypeEnum type) {
    return switch (type) {
      case BIO_DIESEL -> "bio-diesel";
      case DIESEL -> "diesel";
      case ETHANOL -> "ethanol";
      case GASOLINE -> "gasoline";
      case ELECTRICITY -> "electricity";
      case NATURAL_GAS -> "natural-gas";
      case BIO_BAS -> "bio-gas";
      case FOSSIL_GAS -> "fossil-gas";
    };
  }
}
