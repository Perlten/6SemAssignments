package algo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SuffixTrie {

  private List<Node> Nodes = new LinkedList<>();

  public static String wholeWord;

  public void add(String word) {
    word = word.toLowerCase();
    SuffixTrie.wholeWord = word;

    for (int i = 0; i < word.length(); i++) {
      String substring = word;
      if (word.length() != 1) {
        substring = word.substring(i);
      }
      char letter = substring.charAt(0);

      boolean found = false;
      for (Node Node : Nodes) {
        if (Node.getLetter() == letter) {
          Node.addChild(substring, word);
          found = true;
        }
      }
     
      if (!found) {
        Node newNode = new Node(letter, 1);
        this.Nodes.add(newNode);
        newNode.addChild(substring, word);
      }
    }
  }

  public Collection<String> find(String substring) {
    char rootLetter = substring.charAt(0);
    for (Node Node : Nodes) {
      if(Node.getLetter() == rootLetter){
       return Node.find(substring);
      }
    }
    return new LinkedList<>();
  }
}