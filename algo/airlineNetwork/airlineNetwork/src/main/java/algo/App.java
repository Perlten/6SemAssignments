package algo;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;

public class App {
  public static void main(String[] args) throws FileNotFoundException {
    // Reads data in from csv
    List<JsonObject> airlines = Utils.readCsv("data/airlines.csv");
    List<JsonObject> airports = Utils.readCsv("data/airports.csv");
    List<JsonObject> routes = Utils.readCsv("data/routes.csv");
    // Creates graph structur and adds all airports
    AirlineNetworkGraph graph = new AirlineNetworkGraph(airports);

    // Connects all airpots with routes
    for (JsonObject route : routes) {
      graph.addRoute(route);
    }

    Map<String, ShortestGraphTable> res = graph.findShortestTime("CPH", "JZH", null, null);
    List<Airport> res2 = graph.getPath(res, "CPH", "JZH");
    
    Map<String, ShortestGraphTable> res3 = graph.findShortestDistance("CPH", "JZH", null, null);
    List<Airport> res4 = graph.getPath(res3, "CPH", "JZH");
    
    // Checks if we can connect from DMM to ABT with SV (should return true)
    // long currentTime = System.nanoTime();
    // System.out.println(graph.depthFirst("DMM", "ABT", "SV", null));
    // long newTime = System.nanoTime();
    // System.out.println(newTime - currentTime);

    // currentTime = System.nanoTime();
    // System.out.println(graph.breadthFirst("DMM", "ABT", "SV", null, null));
    // newTime = System.nanoTime();
    // System.out.println(newTime - currentTime);

    // // Checks if we can connect from SCQ to TXL with W2 (should return false)
    // System.out.println(graph.depthFirst("SCQ", "TXL", "W2", null));
    // System.out.println(graph.breadthFirst("SCQ", "TXL", "W2", null, null));
  }
}
