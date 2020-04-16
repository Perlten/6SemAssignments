package algo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SuffixTrie {

  private List<Node> Nodes = new LinkedList<>();

  /**
   * @param word Adds a word to our datastructur
   */
  public void add(String word) {
    // makes word lowercase
    word = word.toLowerCase();

    // makes sure we use all suffixes of the word
    for (int i = 0; i < word.length(); i++) {
      // Create a substring for each suffix
      String substring = word;
      // if the word is longer then 1 subtract the the first letter
      if (word.length() != 1) {
        substring = word.substring(i);
      }
      // Takes the first letter in our substring and saves it in letter
      char letter = substring.charAt(0);

      // Iterates each child node and see if we can find the node with the same letter
      // as 'letter'
      // we then call that nodes addChild method
      boolean found = false;
      for (Node Node : Nodes) {
        if (Node.getLetter() == letter) {
          Node.addChild(substring, word, i);
          found = true;
        }
      }
      // If we cant find that node we create a new node with the given letter
      if (!found) {
        Node newNode = new Node(letter, 1);
        this.Nodes.add(newNode);
        newNode.addChild(substring, word, i);
      }
    }
  }

  /**
   * @param substring
   * @return Collection<String> Finds a given substring on out word collection
   */
  public List<String> find(String substring) {
    // We find the first letter and checks if we can find a node with the given
    // letter in out child list
    char rootLetter = substring.charAt(0);
    for (Node Node : Nodes) {
      if (Node.getLetter() == rootLetter) {
        return Node.find(substring);
      }
    }
    return new LinkedList<>();
  }
}