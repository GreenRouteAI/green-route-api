package app.green.route.service;

import app.green.route.model.User;
import app.green.route.model.exception.NotFoundException;
import app.green.route.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
  private final UserRepository repository;

  public User findByAuthenticationIdAndEmail(String firebaseId, String email) {
    return repository
        .findByAuthenticationIdAndEmail(firebaseId, email)
        .orElseThrow(() -> new NotFoundException("User." + email + " is not found"));
  }

  public User getUserById(String userId) {
    return repository
        .findById(userId)
        .orElseThrow(() -> new NotFoundException("User." + userId + " is not found"));
  }

  public User save(User toSave) {
    return repository.save(toSave);
  }
}
