package app.green.route.endpoint.security;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;

import app.green.route.model.exception.ForbiddenException;
import app.green.route.service.UserService;
import app.green.route.service.api.firebase.FirebaseService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConf {
  private final HandlerExceptionResolver exceptionResolver;
  private final AuthProvider provider;
  private final UserService userService;
  private final FirebaseService firebaseService;

  public SecurityConf(
      @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver,
      AuthProvider auth,
      UserService userService,
      FirebaseService firebase) {
    exceptionResolver = resolver;
    provider = auth;
    this.userService = userService;
    this.firebaseService = firebase;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authenticationProvider(provider)
        .addFilterBefore(
            bearerFilter(
                new NegatedRequestMatcher(
                    new OrRequestMatcher(
                        new AntPathRequestMatcher("/ping"),
                        new AntPathRequestMatcher("/weathers"),
                        new AntPathRequestMatcher("/signup", POST.name()),
                        new AntPathRequestMatcher("/**", OPTIONS.toString())))),
            AnonymousAuthenticationFilter.class)
        .authorizeHttpRequests(
            (authorize) ->
                authorize
                    .requestMatchers(OPTIONS, "/**")
                    .permitAll()
                    .requestMatchers("/ping")
                    .permitAll()
                    .requestMatchers("/weathers")
                    .permitAll()
                    .requestMatchers(POST, "/signup")
                    .permitAll()
                    .requestMatchers(POST, "/signin")
                    .authenticated()
                    // requestMatchers(new SelfUserMatcher(GET, "/users/*")) uncomment when self
                    // matcher correctly set
                    .requestMatchers(GET, "/users/*")
                    .authenticated()
                    .requestMatchers(POST, "/itineraries")
                    .authenticated()
                    .requestMatchers(GET, "/activities")
                    .authenticated()
                    .anyRequest()
                    .denyAll())
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .cors(AbstractHttpConfigurer::disable);
    return http.build();
  }

  private Exception forbiddenWithRemoteInfo(Exception e, HttpServletRequest req) {
    log.info(
        String.format(
            "Access is denied for remote caller: address=%s, host=%s, port=%s",
            req.getRemoteAddr(), req.getRemoteHost(), req.getRemotePort()));
    return new ForbiddenException(e.getMessage());
  }

  private AuthFilter bearerFilter(RequestMatcher requestMatcher) throws Exception {
    AuthFilter bearerFilter = new AuthFilter(requestMatcher, userService, firebaseService);
    bearerFilter.setAuthenticationManager(authenticationManager());
    bearerFilter.setAuthenticationSuccessHandler(
        (httpServletRequest, httpServletResponse, authentication) -> {});
    bearerFilter.setAuthenticationFailureHandler(
        (req, res, e) ->
            exceptionResolver.resolveException(req, res, null, forbiddenWithRemoteInfo(e, req)));
    return bearerFilter;
  }

  protected AuthenticationManager authenticationManager() {
    return new ProviderManager(provider);
  }
}
