package algo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Node {
  private char letter;
  private List<Node> children = new LinkedList<>();
  private Collection<String> words = new HashSet<>();
  private int layer;

  public Node(char letter, int layer) {
    this.letter = letter;
    this.layer = layer;
  }

  public void addChild(String subString, String wholeWord) {
    if (subString.length() < 1) {
      return;
    }

    subString = subString.substring(1);

    if (!this.words.contains(wholeWord)) {
      this.words.add(wholeWord);
    }

    if (subString.equals("")) {
      return;
    }

    char letter = subString.charAt(0);
    boolean found = false;

    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        node.addChild(subString, wholeWord);
        found = true;
      }
    }

    if (!found) {
      if (this.words.size() > 2) {
        Node node = new Node(letter, layer + 1);
        children.add(node);
        node.addChild(subString, wholeWord);
      } else if (this.words.size() > 1) {
        String orginalWord = this.words.iterator().next();
        String orginalWordSubstring = orginalWord.substring(this.layer);

        Node node = new Node(letter, layer + 1);
        children.add(node);

        if (orginalWord.length() > wholeWord.length()) {
          node.addChild(subString, wholeWord);
          node.addChild(orginalWordSubstring, orginalWord);
        } else {
          node.addChild(orginalWordSubstring, orginalWord);
          node.addChild(subString, wholeWord);
        }
      }
    }

  }

  public Collection<String> find(String substring) {
    String tempString = substring.substring(1);

    if (tempString.equals("")) {
      return this.words;
    }
    char letter = tempString.charAt(0);

    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        return node.find(tempString);
      }
    }
    LinkedList<String> resList = new LinkedList<>();
    for (String word : this.words) {
      if (word.contains(substring)) {
        resList.add(word);
      }
    }
    return resList;

  }

  public List<Node> getChildren() {
    return children;
  }

  public char getLetter() {
    return letter;
  }
}