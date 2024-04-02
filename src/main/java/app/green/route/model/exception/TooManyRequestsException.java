package app.green.route.model.exception;

import static app.green.route.model.exception.ApiException.ExceptionType.CLIENT_EXCEPTION;

public class TooManyRequestsException extends ApiException {
  public TooManyRequestsException(String message) {
    super(CLIENT_EXCEPTION, message);
  }
}
