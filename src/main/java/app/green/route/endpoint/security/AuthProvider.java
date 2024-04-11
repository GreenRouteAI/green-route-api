package app.green.route.endpoint.security;

import app.green.route.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider extends AbstractUserDetailsAuthenticationProvider {
  private static final String BEARER_PREFIX = "Bearer ";

  private static Principal getPrincipal() {
    SecurityContext context = SecurityContextHolder.getContext();
    Object principal = context.getAuthentication().getPrincipal();
    return ((Principal) principal);
  }

  public static String getBearer(HttpServletRequest req) {
    String authorization = req.getHeader("Authorization");
    if (authorization != null && authorization.startsWith(BEARER_PREFIX)) {
      return authorization.substring(BEARER_PREFIX.length());
    }
    return null;
  }

  public static User getUser() {
    return getPrincipal().getUser();
  }

  public static String getBearer() {
    return getPrincipal().getBearer();
  }

  /**
   * @param userDetails as retrieved from the {@link #retrieveUser(String,
   *     UsernamePasswordAuthenticationToken)} or <code>UserCache</code>
   * @param authentication the current request that needs to be authenticated
   */
  @Override
  protected void additionalAuthenticationChecks(
      UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {}

  @Override
  protected UserDetails retrieveUser(
      String username, UsernamePasswordAuthenticationToken authentication)
      throws AuthenticationException {
    return (Principal) authentication.getPrincipal();
  }
}
