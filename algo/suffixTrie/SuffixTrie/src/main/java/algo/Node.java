package algo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

class Node {
  private char letter;
  private List<Node> children = new LinkedList<>();
  private Collection<String> words = new HashSet<>();
  private int layer;

  public Node(char letter, int layer) {
    this.letter = letter;
    this.layer = layer;
  }

  /**
   * @param subString
   * @param wholeWord Adds a child to our node
   */
  public void addChild(String subString, String wholeWord) {
    // if the given substring is null return from method
    if (subString == null) {
      return;
    }

    // Create new substring with the first letter removed
    subString = subString.substring(1);

    // Checks if wholeWord is already saved in this node. We use a hashset to save
    // time doing this opration
    this.words.add(wholeWord);

    // This is the exit condition and ends our recursion
    if (subString.equals("")) {
      return;
    }

    // we find the first letter in out substring
    char letter = subString.charAt(0);
    boolean found = false;

    // Iterate out children and checks if it has a child with the same letter as our
    // variable 'letter'
    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        node.addChild(subString, wholeWord);
        found = true;
      }
    }

    if (!found) {
      // if the saved words in the node is larger then 2, we can just create a new
      // node with the next letter
      if (this.words.size() > 2) {
        Node node = new Node(letter, layer + 1);
        children.add(node);
        node.addChild(subString, wholeWord);
      } else if (this.words.size() > 1) {
        // else we have to handle a collition where the smallest word get handled first
        // (springer buk)
        String orginalWord = this.words.iterator().next();
        String orginalWordSubstring = orginalWord.substring(this.layer);

        Node node = new Node(letter, layer + 1);
        children.add(node);

        // as the layer grow the orginal substring will be empty therefore we set it to
        // null
        if (orginalWordSubstring.length() <= this.layer) {
          orginalWordSubstring = null;
        }

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

  /**
   * @param substring
   * @return Collection<String> Find a given substring in this node
   */
  public Collection<String> find(String substring) {
    // removes the first letter and checks if string is not empty
    String tempString = substring.substring(1);

    if (tempString.equals("")) {
      return this.words;
    }

    // saves the first letter
    char letter = tempString.charAt(0);

    // iterate our children list and checks if we can find a child with the given
    // letter and then call that childs find method recursively
    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        return node.find(tempString);
      }
    }

    // if we cant find a child node we have to see if one of the words in this node
    // contains the substring because we use a compact trie
    LinkedList<String> resList = new LinkedList<>();
    for (String word : this.words) {
      if (word.contains(substring)) {
        resList.add(word);
      }
    }
    return resList;

  }

  /**
   * @return List<Node>
   */
  public List<Node> getChildren() {
    return children;
  }

  /**
   * @return char
   */
  public char getLetter() {
    return letter;
  }
}