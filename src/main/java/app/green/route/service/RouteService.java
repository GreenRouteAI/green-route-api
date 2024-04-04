package app.green.route.service;

import app.green.route.endpoint.rest.model.Itinerary;
import app.green.route.endpoint.rest.model.ItineraryTransport;
import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.service.api.gemini.GeminiService;
import app.green.route.service.api.travelco.TravelCO2Api;
import app.green.route.service.api.travelco.payload.CarboneFootPrintData;
import app.green.route.service.api.travelco.payload.TravelCO2Payload;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RouteService {
  private final GeminiService geminiService;
  private final TravelCO2Api travelCO2Api;

  public Itinerary generateItineraries(TravelCO2Payload travel, TravelDescription description) {
    CarboneFootPrintData carboneFootPrint = travelCO2Api.evaluateCO2e(travel);
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
    return new Itinerary()
        .travelDescription(promptResponse)
        .transport(
            new ItineraryTransport()
                .co2e(BigDecimal.valueOf(carboneFootPrint.getCo2e()))
                .co2ePp(BigDecimal.valueOf(carboneFootPrint.getCo2ePP())))
        .accommodation(
            new ItineraryTransport()
                .co2e(BigDecimal.valueOf(carboneFootPrint.getCo2e()))
                .co2ePp(BigDecimal.valueOf(carboneFootPrint.getCo2ePP())));
  }
}
