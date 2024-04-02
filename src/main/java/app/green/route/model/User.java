package app.green.route.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "\"user\"")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@ToString
public class User implements Serializable {
  @Id private String id;

  private String firstName;
  private String lastName;

  @Column(unique = true)
  private String email;

  @Timestamp private Instant birthdate;

  @Column(unique = true)
  private String authenticationId;

  @Column(unique = true)
  private String photoId;

  @Column(unique = true)
  private String username;
}
