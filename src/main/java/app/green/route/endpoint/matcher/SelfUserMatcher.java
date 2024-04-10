package app.green.route.endpoint.matcher;

import org.springframework.http.HttpMethod;

public class SelfUserMatcher extends SelfMatcher {
  private final HttpMethod method;
  private final String requestUri;

  public SelfUserMatcher(HttpMethod method, String requestUri) {
    this.method = method;
    this.requestUri = requestUri;
  }
}
