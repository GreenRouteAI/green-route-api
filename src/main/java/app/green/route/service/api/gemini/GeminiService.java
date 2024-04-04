package app.green.route.service.api.gemini;

import app.green.route.service.api.gemini.conf.GeminiConf;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GeminiService {
  private final GeminiConf conf;

  public String generateContent(String prompt) {
    return conf.generateContent(prompt).getCandidates(0).getContent().getParts(0).getText();
  }
}
