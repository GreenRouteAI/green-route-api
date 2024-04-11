package app.green.route.unit;

import static app.green.route.testutils.TestUtils.travelDescription;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.endpoint.validator.TravelValidator;
import app.green.route.model.exception.BadRequestException;
import org.junit.jupiter.api.Test;

class TravelValidatorTest {
  private final TravelValidator subject = new TravelValidator();

  @Test
  void validate_travel_ok() {
    assertDoesNotThrow(() -> subject.accept(travelDescription()));
  }

  @Test
  void validate_travel_ko() {
    assertThrows(BadRequestException.class, () -> subject.accept(unCompletDescription()));
  }

  public TravelDescription unCompletDescription() {
    return new TravelDescription().from("New York").to("Paris");
  }
}
