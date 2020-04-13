package algo;

import java.util.LinkedList;
import java.util.List;

public class RootNode {
  private char letter;
  private List<Node> children = new LinkedList<>();

  public RootNode(char letter) {
    this.letter = letter;
  }

  public void addChild(String word) {
    if (word.length() > 1) {
      word = word.substring(1);
    }
    char letter = word.charAt(0);
    boolean found = false;
    for (Node node : children) {
      if (node.getLetter() == letter) {
        node.addWord(word, false);
        found = true;
      }
    }

    if (!found) {
      Node node = new Node(letter);
      children.add(node);
      node.addWord(word, false);
    }
  }

  public char getLetter() {
    return letter;
  }

  public List<Node> getChildren() {
    return children;
  }
}