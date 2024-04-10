package app.green.route.endpoint.validator;

import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.model.exception.BadRequestException;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class TravelValidator implements Consumer<TravelDescription> {
  @Override
  public void accept(TravelDescription user) {
    var builder = new StringBuilder();
    if (user.getFrom() == null) {
      builder.append("Origin is mandatory. ");
    }
    if (user.getTo() == null) {
      builder.append("Destination is mandatory. ");
    }
    if (user.getPeople() == null) {
      builder.append("People's number is mandatory. ");
    }
    if (user.getAccommodationType() == null) {
      builder.append("AccommodationType is mandatory. ");
    }
    if (user.getVehicle() == null) {
      builder.append("VehicleType is mandatory");
    }
    if (user.getDistance() == null) {
      builder.append("Distance is mandatory. ");
    }

    if (!builder.isEmpty()) {
      throw new BadRequestException(builder.toString());
    }
  }
}
