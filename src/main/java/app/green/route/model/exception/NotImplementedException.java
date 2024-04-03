package app.green.route.model.exception;

import static app.green.route.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

public class NotImplementedException extends ApiException {
  public NotImplementedException(String message) {
    super(SERVER_EXCEPTION, message);
  }
}
