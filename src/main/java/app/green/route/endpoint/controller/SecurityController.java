package app.green.route.endpoint.controller;

import app.green.route.endpoint.mapper.UserRestMapper;
import app.green.route.endpoint.rest.model.User;
import app.green.route.endpoint.security.AuthProvider;
import app.green.route.endpoint.validator.UserValidator;
import app.green.route.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SecurityController {
  private final UserService service;
  private final UserRestMapper mapper;
  private final UserValidator userValidator;

  @PostMapping("/signin")
  public User signIn() {
    var authenticatedUser = AuthProvider.getUser();
    return mapper.toRest(authenticatedUser);
  }

  @PostMapping("/signup")
  public User signUp(@RequestBody User payload) {
    userValidator.accept(payload);
    var toSave = mapper.toDomain(payload);
    var saved = service.save(toSave);
    return mapper.toRest(saved);
  }
}
