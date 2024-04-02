package app.green.route.model.exception;

public class ApiException extends RuntimeException {
  private final ExceptionType type;

  public ApiException(ExceptionType type, String message) {
    super(message);
    this.type = type;
  }

  public enum ExceptionType {
    CLIENT_EXCEPTION,
    SEVER_EXCEPTION
  }
}
