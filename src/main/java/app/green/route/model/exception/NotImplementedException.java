package app.green.route.model.exception;

import static app.green.route.model.exception.ApiException.ExceptionType.SEVER_EXCEPTION;

public class NotImplementedException extends ApiException {
  public NotImplementedException(String message) {
    super(SEVER_EXCEPTION, message);
  }
}
