package app.green.route.endpoint.controller;

import app.green.route.endpoint.mapper.RouteRestMapper;
import app.green.route.endpoint.rest.model.Itinerary;
import app.green.route.endpoint.rest.model.TravelDescription;
import app.green.route.service.RouteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RouteController {
  private final RouteService service;
  private final RouteRestMapper mapper;

  @PostMapping(value = "/itineraries")
  public Itinerary generateItineraries(@RequestBody TravelDescription travelDescription) {
    var payload = mapper.toDomainTravelPayload(travelDescription);
    return service.generateItineraries(payload, travelDescription);
  }
}