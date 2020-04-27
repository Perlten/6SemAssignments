package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public class Airport {
  public final String code;
  public final String name;
  public final String city;
  public final String country;
  public final String latitude;
  public final String longitude;
  public final List<Route> routes = new ArrayList<>();

  public Airport(JsonObject airport){
    this.code = airport.get("code").getAsString();
    this.name = airport.get("name").getAsString();
    this.city = airport.get("city").getAsString();
    this.country = airport.get("country").getAsString();
    this.latitude = airport.get("latitude").getAsString();
    this.longitude = airport.get("longitude").getAsString();
  }



}