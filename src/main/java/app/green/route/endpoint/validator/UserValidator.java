package app.green.route.endpoint.validator;

import app.green.route.endpoint.rest.model.User;
import app.green.route.model.exception.BadRequestException;
import java.util.function.Consumer;
import org.springframework.stereotype.Component;

@Component
public class UserValidator implements Consumer<User> {
  @Override
  public void accept(User user) {
    var builder = new StringBuilder();
    if (user.getId() == null) {
      builder.append("id is mandatory. ");
    }
    if (user.getUsername() == null) {
      builder.append("username is mandatory");
    }
    if (user.getEmail() == null) {
      builder.append("email is mandatory. ");
    }
    if (user.getAuthenticationId() == null) {
      builder.append("AuthenticationId is mandatory. ");
    }

    if (!builder.isEmpty()) {
      throw new BadRequestException(builder.toString());
    }
  }
}
