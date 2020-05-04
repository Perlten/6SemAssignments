package algo;

public class AirportDistanceMST implements Comparable<AirportDistanceMST> {
  public final Airport airport;
  public final double distanceToAirport;

  public AirportDistanceMST(Airport airport, String distanceToAirport) {
    this.airport = airport;
    this.distanceToAirport = Double.parseDouble(distanceToAirport);
  }

  @Override
  public int compareTo(AirportDistanceMST other) {
    return Double.compare(this.distanceToAirport, other.distanceToAirport);
  }


  

}