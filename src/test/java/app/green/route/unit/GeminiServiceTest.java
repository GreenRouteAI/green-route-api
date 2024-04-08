package app.green.route.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import app.green.route.service.api.gemini.GeminiService;
import app.green.route.service.api.gemini.conf.GeminiConf;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeminiServiceTest {
  private GeminiService subject;
  private GeminiConf geminiConfMock;

  @BeforeEach
  void setUp() {
    geminiConfMock = mock();
    subject = new GeminiService(geminiConfMock);

    when(geminiConfMock.getModel())
        .thenReturn(new GenerativeModel("dummy", new VertexAI("dummy", "dummy")));
    when(geminiConfMock.generateContent(any()))
        .thenReturn(
            GenerateContentResponse.newBuilder()
                .addAllCandidates(
                    List.of(
                        new Candidate[] {
                          Candidate.newBuilder()
                              .setContent(
                                  Content.newBuilder()
                                      .addParts(Part.newBuilder().setText("Hi").build())
                                      .build())
                              .build()
                        }))
                .build());
  }

  @Test
  void gemini_prompt_ok() {
    var response = subject.generateContent("Hello");

    assertNotNull(response);
    assertEquals("Hi", response);
  }
}
