package algo;

public class ShortestGraphTable {

  public final double distance;
  public final Airport node;
  public final Airport edge;



  public ShortestGraphTable(double distance, Airport node, Airport edge) {
    this.distance = distance;
    this.node = node;
    this.edge = edge;
  }
}