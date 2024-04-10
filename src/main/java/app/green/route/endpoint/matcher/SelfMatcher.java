package app.green.route.endpoint.matcher;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class SelfMatcher implements RequestMatcher {
  @Override
  public boolean matches(HttpServletRequest request) {
    return true;
  }
}
