package algo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RootNode {
  private char letter;
  private List<Node> children = new LinkedList<>();
  private Collection<String> words = new HashSet<>();

  public RootNode(char letter) {
    this.letter = letter;
  }

  public void addChild(String word) {
    if (!this.words.contains(SuffixTrie.wholeWord)) {
      this.words.add(SuffixTrie.wholeWord);
    }

    if (word.length() > 1) {
      word = word.substring(1);
    } else {
      return;
    }

    char letter = word.charAt(0);
    boolean found = false;
    for (Node node : children) {
      if (node.getLetter() == letter) {
        node.addChild(word);
        found = true;
      }
    }

    if (!found) {
      Node node = new Node(letter);
      children.add(node);
      node.addChild(word);
    }
  }

  public char getLetter() {
    return letter;
  }

  public List<Node> getChildren() {
    return children;
  }

  public Collection<String> find(String substring) {

    // String tempString = substring.substring(1);
    if (substring.equals("")) {
      return this.words;
    }

    char letter = substring.charAt(0);

    for (Node node : children) {
      if (node.getLetter() == letter) {
        return node.find(substring);
      }
    }

    return new LinkedList<>();
  }

}