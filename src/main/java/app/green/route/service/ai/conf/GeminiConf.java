package app.green.route.service.ai.conf;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class GeminiConf {
  private String projectId;
  private String location;
  private String modelType;

  public GeminiConf(@Value("${gemini.project.id}") String pId,
                    @Value("${gemini.location}") String loc,
                    @Value("${gemini.type}") String type){
    projectId = pId;
    location = loc;
    modelType = type;
  }

  @Bean
  public GenerativeModel getModel(){
    var vertexAi = new VertexAI(projectId, location);
    return new GenerativeModel(modelType, vertexAi);
  }

}
