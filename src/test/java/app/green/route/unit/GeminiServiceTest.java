package app.green.route.unit;

import static app.green.route.testutils.TestUtils.setGeminiConf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import app.green.route.service.api.gemini.GeminiService;
import app.green.route.service.api.gemini.conf.GeminiConf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GeminiServiceTest {
  private GeminiService subject;
  private GeminiConf geminiConfMock;

  @BeforeEach
  void setUp() {
    geminiConfMock = mock();
    subject = new GeminiService(geminiConfMock);
    setGeminiConf(geminiConfMock);
  }

  @Test
  void gemini_prompt_ok() {
    var response = subject.generateContent("Hello");

    assertNotNull(response);
    assertEquals("Hi", response);
  }
}
