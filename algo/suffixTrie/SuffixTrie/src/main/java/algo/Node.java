package algo;

import java.util.LinkedList;
import java.util.List;

public class Node {
  private char letter;
  List<Node> children = new LinkedList<>();

  public Node(char letter) {
    this.letter = letter;
  }

  public void addWord(String word, boolean last) {
    if (last) {
      return;
    }

    boolean isLast = false;
    word = word.substring(1);
    if (word.length() == 1) {
      isLast = true;
    }

    char letter = word.charAt(0);
    boolean found = false;

    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        node.addWord(word, isLast);
        found = true;
      }
    }

    if (!found) {
      Node node = new Node(letter);
      children.add(node);
      node.addWord(word, isLast);
    }

  }

  public List<Node> getChildren() {
    return children;
  }

  public char getLetter() {
    return letter;
  }
}