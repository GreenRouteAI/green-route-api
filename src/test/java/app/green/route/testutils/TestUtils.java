package app.green.route.testutils;

import static app.green.route.endpoint.rest.model.Fuel.TypeEnum.DIESEL;
import static app.green.route.endpoint.rest.model.TravelDescription.AccommodationTypeEnum.HOSTEL;
import static app.green.route.endpoint.rest.model.Vehicle.TypeEnum.SMALL_CAR;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import app.green.route.endpoint.rest.client.ApiClient;
import app.green.route.endpoint.rest.model.Fuel;
import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.endpoint.rest.model.User;
import app.green.route.endpoint.rest.model.Vehicle;
import app.green.route.service.api.firebase.FUser;
import app.green.route.service.api.firebase.FirebaseService;
import app.green.route.service.api.gemini.conf.GeminiConf;
import app.green.route.service.api.travelco.TravelCO2Api;
import app.green.route.service.api.travelco.payload.AccommodationCarboneFootPrint;
import app.green.route.service.api.travelco.payload.AccommodationPayload;
import app.green.route.service.api.travelco.payload.TransportCarboneFootPrint;
import app.green.route.service.api.travelco.payload.TransportPayload;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class TestUtils {
  public static String VALID_TOKEN = "valid_token";
  public static String BAD_TOKEN = "bad_token";
  private static String USER1_ID = "user1_id";
  private static String USER1_AUTHENTICATION_ID = "user1_authentication_id";

  public static ApiClient anApiClient(String token, int serverPort) {
    ApiClient client = new ApiClient();
    client.setScheme("http");
    client.setHost("localhost");
    client.setPort(serverPort);
    client.setRequestInterceptor(
        httpRequestBuilder -> httpRequestBuilder.header("Authorization", "Bearer " + token));
    return client;
  }

  public static void setGeminiConf(GeminiConf geminiConfMock) {
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

  public static void setFirebaseService(FirebaseService firebaseService) {
    when(firebaseService.getUserByBearer(VALID_TOKEN))
        .thenReturn(new FUser(USER1_AUTHENTICATION_ID, "user1@email.com"));
  }

  public static void setTravelApi(TravelCO2Api travelApi) {
    when(travelApi.evaluateTransport(any(TransportPayload.class)))
        .thenReturn(
            TransportCarboneFootPrint.builder()
                .co2e(10.3)
                .co2ePP(2.36)
                .distance(5000)
                .people(2)
                .vehicle(new TransportCarboneFootPrint.Vehicle())
                .build());
    when(travelApi.evaluateAccommodation(any(AccommodationPayload.class)))
        .thenReturn(
            AccommodationCarboneFootPrint.builder()
                .co2e(10.3)
                .co2ePP(2.36)
                .people(2)
                .nights(2)
                .type("hotel")
                .build());
  }

  public static User user1() {
    return new User()
        .id(USER1_ID)
        .birthDate(null)
        .firstName("nyhasina")
        .lastName("vagno")
        .username("nyhasina14")
        .email("user1@email.com")
        .photoId("photo_id")
        .authenticationId(USER1_AUTHENTICATION_ID);
  }

  public static User toCreate() {
    return new User()
        .id("user2_id")
        .birthDate(null)
        .firstName("user2")
        .lastName("user2_lastname")
        .username("user214")
        .email("user2@email.com")
        .photoId("photo2_id")
        .authenticationId("user2_auth_id");
  }

  public static int anAvailablePort() {
    try {
      return new ServerSocket(0).getLocalPort();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static TravelDescription travelDescription() {
    return new TravelDescription()
        .from("Paris")
        .to("London")
        .distance(5000)
        .people(2)
        .nights(7)
        .vehicle(new Vehicle().type(SMALL_CAR).fuel(new Fuel().type(DIESEL)))
        .accommodationType(HOSTEL);
  }
}
