package algo;

import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) {
    Node rootNode = createRegexGraph("k|ni");
    Map<Character, Node> textGraph = createTextGraph("in ni kl ki");
    boolean res = findSubstring(rootNode, textGraph);
    System.out.println(res);

  }

  public static boolean findSubstring(Node regexRootNode, Map<Character, Node> textGraph) {
    for (Character currentKey : regexRootNode.getConnections().keySet()) {
      if (!textGraph.containsKey(currentKey)) {
        break;
      }
      Node textNode = textGraph.get(currentKey);
      Node regexNode = regexRootNode.getNode(currentKey);
      boolean res = searchNode(regexNode, textNode);
      if (res) {
        return true;
      }
    }

    return false;
  }

  public static boolean searchNode(Node regexNode, Node textNode) {
    for (Character currentKey : regexNode.getConnections().keySet()) {
      if (textNode.containsConnections(currentKey)) {
        return searchNode(regexNode.getNode(currentKey), textNode.getNode(currentKey));
      }
      return false;
    }
    return true;
  }

  public static Map<Character, Node> createTextGraph(String text) {
    Map<Character, Node> textGraph = new HashMap<>();
    Node preNode = null;

    String[] textList = text.split(" ");
    for (String subString : textList) {

      String tempText = subString;

      while (!tempText.equals("")) {
        char currentLetter = tempText.charAt(0);
        tempText = tempText.substring(1);

        if (!textGraph.containsKey(currentLetter)) {
          textGraph.put(currentLetter, new Node());
        }
        Node currentNode = textGraph.get(currentLetter);
        if (preNode != null) {
          preNode.addNode(currentLetter, currentNode);
        }
        preNode = currentNode;
      }
    }

    return textGraph;

  }

  public static Node createRegexGraph(String regex) {
    Node rootNode = new Node();
    Node node = rootNode;
    Node preNode = null;
    boolean wasOr = false;

    for (int i = 0; i < regex.length(); i++) {
      char currentLetter = regex.charAt(i);
      if (wasOr) {
        wasOr = false;
        Node newNode = new Node();
        for (Character ch : preNode.getConnections().keySet()) {
          preNode.getNode(ch).addNode(currentLetter, newNode);
        }
        preNode = newNode;
        node = newNode;
        continue;
      }
      if (currentLetter == '|') {
        wasOr = true;
        i++;
        currentLetter = regex.charAt(i);
        preNode.addNode(currentLetter);
      } else {
        preNode = node;
        node = node.addNode(currentLetter);
      }
    }

    return rootNode;
  }
}
