package app.green.route.repository;

import app.green.route.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByAuthenticationIdAndEmail(String firebaseId, String email);
}
