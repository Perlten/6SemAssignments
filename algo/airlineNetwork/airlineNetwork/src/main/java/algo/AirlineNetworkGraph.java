package algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.google.gson.JsonObject;

public class AirlineNetworkGraph {

  // Map with all the airlines
  public final Map<String, Airport> airportMap = new HashMap<>();

  public AirlineNetworkGraph(List<JsonObject> airports) {
    // Loops trough all the airports and adds them to our hashmap
    for (JsonObject airport : airports) {
      if (!airportMap.containsKey(airport.get("code").getAsString())) {
        airportMap.put(airport.get("code").getAsString(), new Airport(airport));
      }
    }
  }

  public void addRoute(JsonObject route) {
    // Adds a route the correct airport
    String airportCode = route.get("source_code").getAsString();
    if (!this.airportMap.containsKey(airportCode)) {
      return;
    }

    this.airportMap.get(airportCode).routes.add(new Route(route, this.airportMap));
  }

  public boolean depthFirst(String source, String dest, String airlineCode, Set<String> visitedAirports) {
    // in order to keep track of our visited airports we use a hashset to make sure
    // we dont check the same airport twice
    if (visitedAirports == null) {
      visitedAirports = new HashSet<>();
      visitedAirports.add(source);
    }

    // gets the current airport
    Airport airport = airportMap.get(source);
    // Check if its the correct airport
    if (airport.code.equals(dest)) {
      return true;
    }

    // itterates over all the airports routes and checks if is from the airline we
    // want and we have not visited it before
    for (Route route : airport.routes) {
      if (route.airlineCode.equals(airlineCode)) {
        if (visitedAirports.contains(route.destCode)) {
          continue;
        }
        visitedAirports.add(route.destCode);
        // call the method recursive with routes dest airport code
        boolean res = depthFirst(route.destCode, dest, airlineCode, visitedAirports);
        if (res) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean breadthFirst(String source, String dest, String airlineCode, Queue<String> airportQueue,
      Set<String> visitedAirports) {
    // creates the lists if value is null
    if (airportQueue == null) {
      airportQueue = new LinkedList<>();
    }

    if (visitedAirports == null) {
      visitedAirports = new HashSet<>();
      // adds the first current airport code on first node
      visitedAirports.add(source);
    }

    // gets the current airport
    Airport airport = airportMap.get(source);

    // checks if current airport is the final airport
    if (airport.code.equals(dest)) {
      return true;
    }

    // iterate route for current airport and adds all child nodes to queue
    // if we havent already looked at them
    for (Route route : airport.routes) {
      if (route.airlineCode.equals(airlineCode)) {
        if (!visitedAirports.contains(route.destCode)) {
          airportQueue.add(route.destCode);
          visitedAirports.add(route.destCode);
        }
      }
    }

    // If a this point the queue is empty we can return false
    if (airportQueue.isEmpty()) {
      return false;
    }

    // We remove the top of the queue and calls the method again this time with the
    // next airport code as source
    String nextAirport = airportQueue.remove();
    return breadthFirst(nextAirport, dest, airlineCode, airportQueue, visitedAirports);
  }

  public Map<String, ShortestGraphTable> findShortestDistance(String current, String dest,
      Map<String, ShortestGraphTable> nodeTable, Queue<String> airportQueue) {
    if (nodeTable == null) {
      nodeTable = new HashMap<>();
      nodeTable.put(current, new ShortestGraphTable(0, this.airportMap.get(current), null));
    }
    if (airportQueue == null) {
      airportQueue = new LinkedList<>();
    }

    if (current.equals(dest)) {
      return nodeTable;
    }

    Airport currentAirport = this.airportMap.get(current);

    for (Route route : currentAirport.routes) {
      double routeDistance = Double.parseDouble(route.distance);
      double currentNodeDistance = nodeTable.get(current).distance + routeDistance;

      if (!nodeTable.containsKey(route.airport.code)) {
        airportQueue.add(route.airport.code);
        nodeTable.put(route.airport.code, new ShortestGraphTable(currentNodeDistance, route.airport, currentAirport));
      } else {
        double nextDistance = nodeTable.get(route.airport.code).distance;
        if (currentNodeDistance < nextDistance) {
          airportQueue.add(route.airport.code);
          nodeTable.put(route.airport.code, new ShortestGraphTable(currentNodeDistance, route.airport, currentAirport));
        }
      }
    }

    if (airportQueue.isEmpty()) {
      return nodeTable;
    }
    String nextAirportCode = airportQueue.remove();
    return findShortestDistance(nextAirportCode, dest, nodeTable, airportQueue);
  }

  public Map<String, ShortestGraphTable> findShortestTime(String current, String dest,
      Map<String, ShortestGraphTable> nodeTable, Queue<String> airportQueue) {
    if (nodeTable == null) {
      nodeTable = new HashMap<>();
      nodeTable.put(current, new ShortestGraphTable(0, this.airportMap.get(current), null));
    }
    if (airportQueue == null) {
      airportQueue = new LinkedList<>();
    }

    if (current.equals(dest)) {
      return nodeTable;
    }

    Airport currentAirport = this.airportMap.get(current);

    for (Route route : currentAirport.routes) {
      double routeTime = Double.parseDouble(route.distanceTime);
      double currentNodeTime = nodeTable.get(current).distance + routeTime;

      if (!nodeTable.containsKey(route.airport.code)) {
        airportQueue.add(route.airport.code);
        nodeTable.put(route.airport.code, new ShortestGraphTable(currentNodeTime + 1, route.airport, currentAirport));
      } else {
        double nextTime = nodeTable.get(route.airport.code).distance;
        if (currentNodeTime < nextTime) {
          airportQueue.add(route.airport.code);
          nodeTable.put(route.airport.code, new ShortestGraphTable(currentNodeTime + 1, route.airport, currentAirport));
        }
      }
    }

    if (airportQueue.isEmpty()) {
      return nodeTable;
    }
    String nextAirportCode = airportQueue.remove();
    return findShortestDistance(nextAirportCode, dest, nodeTable, airportQueue);
  }

  public List<Airport> getPath(Map<String, ShortestGraphTable> map, String from, String dest) {
    String current = dest;
    List<Airport> res = new ArrayList<>();
    if(!map.containsKey(current)){
      return new ArrayList<>();
    }
    res.add(map.get(current).node);

    while (!current.equals(from)) {
      ShortestGraphTable sgt = map.get(current);
      Airport currentAirport = sgt.edge;
      res.add(currentAirport);
      current = sgt.edge.code;
    }
    return res;
  }


  public String widestCoverageAirline(List<JsonObject> airlines){
    for(JsonObject airline : airlines){
      
    }


    return null;
  }

}
