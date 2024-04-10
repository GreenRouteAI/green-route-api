package app.green.route.endpoint.controller;

import app.green.route.endpoint.rest.model.InlineObject;
import app.green.route.endpoint.rest.model.Weather;
import app.green.route.service.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class WeatherController {
  private final WeatherService service;

  @PostMapping("/weathers")
  public Weather getForecasts(@RequestBody InlineObject origDest) {
    return service.getForecast(origDest);
  }
}
