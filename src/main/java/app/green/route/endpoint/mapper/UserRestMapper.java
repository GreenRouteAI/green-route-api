package app.green.route.endpoint.mapper;

import app.green.route.endpoint.rest.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRestMapper {

  public User toRest(app.green.route.model.User domain) {
    return new User()
        .id(domain.getId())
        .firstName(domain.getFirstName())
        .lastName(domain.getLastName())
        .birthDate(domain.getBirthdate())
        .authenticationId(domain.getAuthenticationId())
        .email(domain.getEmail())
        .photoId(domain.getPhotoId());
  }

  public app.green.route.model.User toDomain(User rest) {
    return app.green.route.model.User.builder()
        .id(rest.getId())
        .firstName(rest.getFirstName())
        .lastName(rest.getLastName())
        .birthdate(rest.getBirthDate())
        .authenticationId(rest.getAuthenticationId())
        .email(rest.getEmail())
        .photoId(rest.getPhotoId())
        .build();
  }
}
