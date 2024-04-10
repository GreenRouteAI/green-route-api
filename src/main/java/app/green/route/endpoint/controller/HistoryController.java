package app.green.route.endpoint.controller;

import app.green.route.endpoint.rest.model.Itinerary;
import app.green.route.model.History;
import app.green.route.service.HistoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class HistoryController {
  private final HistoryService service;

  @GetMapping("/activities")
  public List<Itinerary> getActivities() {
    return service.getHistory().stream().map(History::getItinerary).collect(Collectors.toList());
  }
}
