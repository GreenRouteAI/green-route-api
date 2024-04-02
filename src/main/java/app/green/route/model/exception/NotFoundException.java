package app.green.route.model.exception;

import static app.green.route.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class NotFoundException extends ApiException{
  public NotFoundException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
