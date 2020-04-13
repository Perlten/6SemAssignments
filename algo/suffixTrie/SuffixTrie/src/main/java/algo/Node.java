package algo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Node {
  private char letter;
  private List<Node> children = new LinkedList<>();
  private Collection<String> words = new HashSet<>();

  public Node(char letter) {
    this.letter = letter;
  }

  public void addWord(String word) {
    word = word.substring(1);

    if (!this.words.contains(SuffixTrie.wholeWord)) {
      this.words.add(SuffixTrie.wholeWord);
    }

    if (word.equals("")) {
      return;
    }

    char letter = word.charAt(0);
    boolean found = false;

    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        node.addWord(word);
        found = true;
      }
    }

    if (!found) {
      Node node = new Node(letter);
      children.add(node);
      node.addWord(word);
    }

  }

  public Collection<String> find(String substring) {
    String tempString = substring.substring(1);

    if (tempString.equals("")) {
      return this.words;
    }
    char letter = tempString.charAt(0);

    for (Node node : children) {
      if (node.getLetter() == letter) {
        return node.find(tempString);
      }
    }
    return new LinkedList<>();
  }

  public List<Node> getChildren() {
    return children;
  }

  public char getLetter() {
    return letter;
  }
}