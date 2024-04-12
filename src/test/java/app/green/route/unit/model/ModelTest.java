package app.green.route.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.green.route.service.api.travelco.payload.AccommodationCarboneFootPrint;
import app.green.route.service.api.travelco.payload.AccommodationPayload;
import app.green.route.service.api.travelco.payload.TransportCarboneFootPrint;
import app.green.route.service.api.travelco.payload.TransportPayload;
import app.green.route.service.api.weather.payload.WeatherResponse;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ModelTest {

  private static WeatherResponse getWeatherResponse() {
    WeatherResponse.Location location = new WeatherResponse.Location("City", "Region", "Country");
    WeatherResponse.Condition condition = new WeatherResponse.Condition("Cloudy", "cloudy-icon");
    WeatherResponse.Day day = new WeatherResponse.Day("25", "15", condition);
    WeatherResponse.ForecastDay forecastDay = new WeatherResponse.ForecastDay("2024-04-10", day);
    WeatherResponse.Forecast forecast = new WeatherResponse.Forecast(List.of(forecastDay));
    return new WeatherResponse(location, forecast);
  }

  @Test
  public void test_accommodation_carbone_footprint() {
    AccommodationCarboneFootPrint accommodation = new AccommodationCarboneFootPrint();
    accommodation.setType("Hotel");
    accommodation.setTitle("Example Hotel");
    accommodation.setNights(5);
    accommodation.setPeople(2);
    accommodation.setCo2e(50.0);
    accommodation.setCo2ePP(25.0);

    assertEquals("Hotel", accommodation.getType());
    assertEquals("Example Hotel", accommodation.getTitle());
    assertEquals(5, accommodation.getNights());
    assertEquals(2, accommodation.getPeople());
    assertEquals(50.0, accommodation.getCo2e());
    assertEquals(25.0, accommodation.getCo2ePP());
  }

  @Test
  public void test_accommodation_payload() {
    AccommodationPayload payload = new AccommodationPayload();
    payload.setType("Hotel");
    payload.setPeople(2);
    payload.setNights(5);

    assertEquals("Hotel", payload.getType());
    assertEquals(2, payload.getPeople());
    assertEquals(5, payload.getNights());
  }

  @Test
  public void test_transport_carbone_footprint() {
    TransportCarboneFootPrint.Vehicle vehicle = new TransportCarboneFootPrint.Vehicle();
    vehicle.setType("Car");
    vehicle.setTitle("Toyota");
    vehicle.setVehicleCount(1);
    vehicle.setFuel(new TransportCarboneFootPrint.Fuel("Gasoline", "Regular"));
    vehicle.setConsumption("10 km/l");

    TransportCarboneFootPrint transport = new TransportCarboneFootPrint();
    transport.setType("Car");
    transport.setTitle("Example Journey");
    transport.setDistance(100);
    transport.setPeople(2);
    transport.setWays(1);
    transport.setCo2e(50.0);
    transport.setCo2ePP(25.0);
    transport.setVehicle(vehicle);

    assertEquals("Car", transport.getType());
    assertEquals("Example Journey", transport.getTitle());
    assertEquals(100, transport.getDistance());
    assertEquals(2, transport.getPeople());
    assertEquals(1, transport.getWays());
    assertEquals(50.0, transport.getCo2e());
    assertEquals(25.0, transport.getCo2ePP());
    assertEquals(vehicle, transport.getVehicle());
  }

  @Test
  public void test_transport_payload() {
    TransportPayload.Fuel fuel = new TransportPayload.Fuel("Gasoline", "Regular");
    TransportPayload.Vehicle vehicle = new TransportPayload.Vehicle("Car", "Toyota", fuel);

    TransportPayload payload = new TransportPayload();
    payload.setDistance(100.0);
    payload.setPeople(2);
    payload.setVehicle(vehicle);

    assertEquals(100.0, payload.getDistance());
    assertEquals(2, payload.getPeople());
    assertEquals(vehicle, payload.getVehicle());
  }

  @Test
  public void test_weather_response() {
    WeatherResponse weatherResponse = getWeatherResponse();

    assertEquals("City", weatherResponse.getLocation().getName());
    assertEquals("Region", weatherResponse.getLocation().getRegion());
    assertEquals("Country", weatherResponse.getLocation().getCountry());
    assertEquals("2024-04-10", weatherResponse.getForecast().getForecastDay().get(0).getDate());
    assertEquals(
        "25", weatherResponse.getForecast().getForecastDay().get(0).getDay().getMaxTempC());
    assertEquals(
        "15", weatherResponse.getForecast().getForecastDay().get(0).getDay().getMinTempC());
    assertEquals(
        "Cloudy",
        weatherResponse.getForecast().getForecastDay().get(0).getDay().getCondition().getText());
    assertEquals(
        "cloudy-icon",
        weatherResponse.getForecast().getForecastDay().get(0).getDay().getCondition().getIcon());
  }
}
