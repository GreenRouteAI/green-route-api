package app.green.route.model.exception;

import static app.green.route.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class ForbiddenException extends ApiException{
  public ForbiddenException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
