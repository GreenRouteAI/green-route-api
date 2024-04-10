package app.green.route.service;

import app.green.route.endpoint.rest.model.Forecast;
import app.green.route.endpoint.rest.model.ForecastDay;
import app.green.route.endpoint.rest.model.ForecastDayDay;
import app.green.route.endpoint.rest.model.ForecastDayDayCondition;
import app.green.route.endpoint.rest.model.ForecastLocation;
import app.green.route.endpoint.rest.model.InlineObject;
import app.green.route.endpoint.rest.model.Weather;
import app.green.route.service.api.weather.WeatherApi;
import app.green.route.service.api.weather.payload.WeatherResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WeatherService {
  private final WeatherApi weatherApi;

  public Weather getForecast(InlineObject origDest) {
    WeatherResponse origin = weatherApi.getForecasts(origDest.getOrigin());
    WeatherResponse destination = weatherApi.getForecasts(origDest.getDestination());
    return new Weather()
        .origin(
            new Forecast()
                .location(
                    new ForecastLocation()
                        .name(origin.getLocation().getName())
                        .country(origin.getLocation().getCountry())
                        .region(origin.getLocation().getRegion()))
                .forecast(forecastDayList(origin)))
        .destination(
            new Forecast()
                .location(
                    new ForecastLocation()
                        .name(destination.getLocation().getName())
                        .country(destination.getLocation().getCountry())
                        .region(destination.getLocation().getRegion()))
                .forecast(forecastDayList(destination)));
  }

  private List<ForecastDay> forecastDayList(WeatherResponse weatherResponse) {
    return weatherResponse.getForecast().getForecastDay().stream().map(this::forecastDay).toList();
  }

  private ForecastDay forecastDay(WeatherResponse.ForecastDay forecastDay) {
    return new ForecastDay()
        .date(forecastDay.getDate())
        .day(
            new ForecastDayDay()
                .maxTemp(Integer.parseInt(forecastDay.getDay().getMaxTempC()))
                .minTemp(Integer.parseInt(forecastDay.getDay().getMinTempC()))
                .condition(
                    new ForecastDayDayCondition()
                        .text(forecastDay.getDay().getCondition().getText())
                        .icon(forecastDay.getDay().getCondition().getIcon())));
  }
}
