package app.green.route.service.api.gemini.conf;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import java.io.ByteArrayInputStream;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GeminiConf {
  private final String projectId;
  private final String location;
  private final String modelType;
  private final String serviceAccountKey;

  public GeminiConf(
      @Value("${gemini.project.id}") String pId,
      @Value("${gemini.location}") String loc,
      @Value("${gemini.type}") String type,
      @Value("${gemini.api.key}") String key) {
    projectId = pId;
    location = loc;
    modelType = type;
    serviceAccountKey = key;
  }

  @Bean
  @SneakyThrows
  public GenerativeModel getModel() {
    var credentials =
        GoogleCredentials.fromStream(new ByteArrayInputStream(serviceAccountKey.getBytes()));
    var vertexAi =
        new VertexAI.Builder()
            .setProjectId(projectId)
            .setLocation(location)
            .setCredentials(credentials)
            .build();
    return new GenerativeModel(modelType, vertexAi);
  }
}
