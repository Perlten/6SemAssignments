package algo;

import java.util.HashMap;
import java.util.Map;

public class Node {

  private Map<Character, Node> connections = new HashMap<>(); 
  
  public Node addNode(Character connectionValue){
    Node node = new Node();
    this.connections.put(connectionValue, node);
    return node;
  }

  public Node getNode(Character connectionValue){
    return this.connections.get(connectionValue); 
  }

  public boolean containsConnections(Character key){
    return this.connections.containsKey(key);
  }

  public Node addNode(Character connectionValue, Node node) {
    this.connections.put(connectionValue, node);
    return node;
  }

  public boolean hasConnections(){
    return this.connections.size() > 0;
  }

  /**
   * @return the connections
   */
  public Map<Character, Node> getConnections() {
    return connections;
  }

}