package app.green.route.service;

import static java.util.UUID.randomUUID;

import app.green.route.endpoint.rest.model.Itinerary;
import app.green.route.endpoint.rest.model.ItineraryTransport;
import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.endpoint.security.AuthProvider;
import app.green.route.model.History;
import app.green.route.repository.HistoryRepository;
import app.green.route.service.api.gemini.GeminiService;
import app.green.route.service.api.travelco.TravelCO2Api;
import app.green.route.service.api.travelco.payload.AccommodationCarboneFootPrint;
import app.green.route.service.api.travelco.payload.AccommodationPayload;
import app.green.route.service.api.travelco.payload.TransportCarboneFootPrint;
import app.green.route.service.api.travelco.payload.TransportPayload;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RouteService {
  private final GeminiService geminiService;
  private final TravelCO2Api travelCO2Api;
  private final HistoryRepository historyRepository;

  public Itinerary generateItineraries(
      TransportPayload transport,
      AccommodationPayload accommodation,
      TravelDescription description) {
    TransportCarboneFootPrint transCarboneFootPrint = travelCO2Api.evaluateTransport(transport);
    AccommodationCarboneFootPrint accCarboneFootPrint =
        travelCO2Api.evaluateAccommodation(accommodation);
    String promptResponse =
        geminiService.generateContent(
            String.format(
                "Eco-friendly itinerary between %s and %s for %s persons by %s for %s nights in a"
                    + " %s.",
                description.getFrom(),
                description.getTo(),
                description.getPeople(),
                description.getVehicle().getType(),
                description.getNights(),
                description.getAccommodationType()));
    String title = description.getFrom() + " to " + description.getTo();
    var itinerary =
        itineraryFrom(title, promptResponse, transCarboneFootPrint, accCarboneFootPrint);
    var authenticatedUser = AuthProvider.getUser();
    var history =
        History.builder()
            .id(randomUUID().toString())
            .user(authenticatedUser)
            .creationDatetime(Instant.now())
            .itinerary(itinerary)
            .build();
    return historyRepository.save(history).getItinerary();
  }

  private Itinerary itineraryFrom(
      String title,
      String promptResponse,
      TransportCarboneFootPrint carboneFootPrint,
      AccommodationCarboneFootPrint accCarboneFootPrint) {
    return new Itinerary()
        .travelDescription(promptResponse)
        .title(title)
        .transport(
            new ItineraryTransport()
                .co2e(BigDecimal.valueOf(carboneFootPrint.getCo2e()))
                .co2ePp(BigDecimal.valueOf(carboneFootPrint.getCo2ePP())))
        .accommodation(
            new ItineraryTransport()
                .co2e(BigDecimal.valueOf(accCarboneFootPrint.getCo2e()))
                .co2ePp(BigDecimal.valueOf(accCarboneFootPrint.getCo2ePP())));
  }
}
