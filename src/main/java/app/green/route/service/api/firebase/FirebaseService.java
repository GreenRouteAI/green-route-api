package app.green.route.service.api.firebase;

import static app.green.route.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.green.route.model.exception.ApiException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import java.io.ByteArrayInputStream;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FirebaseService {
  private static final Object lock = new Object();
  private static boolean firebaseAppInitialized = false;

  public final String privateKey;

  public FirebaseService(@Value("${firebase.private.key}") String privateKey) {
    this.privateKey = privateKey;
  }

  private void initializeFirebaseApp() {
    synchronized (lock) {
      if (!firebaseAppInitialized) {
        FirebaseOptions options =
            new FirebaseOptions.Builder().setCredentials(getCredentials()).build();

        FirebaseApp.initializeApp(options);
        firebaseAppInitialized = true;
      }
    }
  }

  @SneakyThrows
  public FirebaseAuth auth() {
    initializeFirebaseApp();
    return FirebaseAuth.getInstance();
  }

  @SneakyThrows
  private GoogleCredentials getCredentials() {
    var stream = new ByteArrayInputStream(privateKey.getBytes());
    return GoogleCredentials.fromStream(stream);
  }

  public FUser getUserByBearer(String bearer) {
    if (bearer == null || bearer.isEmpty()) {
      throw new IllegalArgumentException("Bearer token must not be null or empty");
    }

    try {
      FirebaseToken token = auth().verifyIdToken(bearer);
      return token == null
          ? null
          : FUser.builder().id(token.getUid()).email(token.getEmail()).build();
    } catch (FirebaseAuthException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
  }
}
