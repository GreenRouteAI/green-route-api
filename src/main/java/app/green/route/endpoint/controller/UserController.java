package app.green.route.endpoint.controller;

import app.green.route.endpoint.mapper.UserRestMapper;
import app.green.route.endpoint.rest.model.User;
import app.green.route.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {
  private final UserService service;
  private final UserRestMapper mapper;

  @GetMapping("/users/{id}")
  public User getUserById(@PathVariable("id") String uId) {
    var user = service.getUserById(uId);
    return mapper.toRest(user);
  }
}
