package app.green.route.service.api.gemini;

import app.green.route.service.api.gemini.conf.GeminiConf;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeminiService {
  private final GeminiConf conf;

  public GenerateContentResponse generateContent(String prompt) {
    try {
      return conf.getModel().generateContent(prompt);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
