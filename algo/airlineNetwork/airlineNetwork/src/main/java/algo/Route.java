package algo;

import java.util.Map;

import com.google.gson.JsonObject;

public class Route {

  public final String airlineCode;
  public final String sourceCode;
  public final String destCode;
  public final String distance;
  public final String distanceTime;
  public final Airport airport;

  public Route(JsonObject route, Map<String, Airport> airportMap){
    this.airlineCode = route.get("airline_code").getAsString();
    this.sourceCode = route.get("source_code").getAsString();
    this.destCode = route.get("destination_code").getAsString();
    this.distance = route.get("distance").getAsString();
    this.distanceTime = route.get("time").getAsString();
    this.airport = airportMap.get(this.destCode);
  }
}