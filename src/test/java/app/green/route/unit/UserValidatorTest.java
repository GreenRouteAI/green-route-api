package app.green.route.unit;

import static app.green.route.testutils.TestUtils.user1;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import app.green.route.endpoint.rest.model.User;
import app.green.route.endpoint.validator.UserValidator;
import app.green.route.model.exception.BadRequestException;
import org.junit.jupiter.api.Test;

public class UserValidatorTest {
  private UserValidator subject = new UserValidator();

  @Test
  void validate_user_ok() {
    assertDoesNotThrow(() -> subject.accept(user1()));
  }

  @Test
  void validate_user_ko() {
    assertThrows(BadRequestException.class, () -> subject.accept(unCompletUser()));
  }

  public User unCompletUser() {
    return new User().lastName("test").firstName("test");
  }
}
