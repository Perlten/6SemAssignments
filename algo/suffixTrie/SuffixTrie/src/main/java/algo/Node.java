package algo;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

class Node {
  private char letter;
  private List<Node> children = new LinkedList<>();
  private String word;
  private int layer;

  public Node(char letter, int layer) {
    this.letter = letter;
    this.layer = layer;
  }

  /**
   * @param subString
   * @param wholeWord Adds a child to our node
   */
  public void addChild(String subString, String wholeWord, int wordCount) {
    // if the given substring is null return from method
    if (subString == null) {
      this.word = wholeWord;
      return;
    }

    // Create new substring with the first letter removed
    subString = subString.substring(1);

    // This is the exit condition and ends our recursion
    if (subString.equals("")) {
      this.word = wholeWord;
      return;
    }

    // we find the first letter in out substring
    char letter = subString.charAt(0);

    boolean found = false;

    // Iterate out children and checks if it has a child with the same letter as our
    // variable 'letter'
    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        node.addChild(subString, wholeWord, wordCount + 1);
        found = true;
      }
    }

    if (!found) {
      if (this.word == null && this.children.size() == 0) {
        this.word = wholeWord;
      } else {
        String orginalWord = this.word;
        if (orginalWord == null || orginalWord.length() == this.layer) {
          Node node = new Node(letter, layer + 1);
          children.add(node);
          node.addChild(subString, wholeWord, wordCount + 1);
          return;
        }

        String orginalWordSubstring = orginalWord.substring(wordCount);

        Node node = new Node(letter, layer + 1);
        children.add(node);

        // as the layer grow the orginal substring will be empty therefore we set it
        // to null
        if (orginalWordSubstring.length() <= this.layer) {
          orginalWordSubstring = null;
        }

        if (orginalWord.length() > wholeWord.length()) {
          node.addChild(subString, wholeWord, wordCount + 1);
          node.addChild(orginalWordSubstring, orginalWord, wordCount + 1);
        } else {
          node.addChild(orginalWordSubstring, orginalWord, wordCount + 1);
          node.addChild(subString, wholeWord, wordCount + 1);
        }

        this.word = null;
      }
    }
  }

  public List<String> getWordFromChildren() {
    List<String> resList = new LinkedList<>();
    if (this.word != null) {
      resList.add(this.word);
    }
    for (Node node : this.children) {
      resList.addAll(node.getWordFromChildren());
    }
    return resList;
  }

  /**
   * @param substring
   * @return Collection<String> Find a given substring in this node
   */
  public List<String> find(String substring) {
    // removes the first letter and checks if string is not empty
    String tempString = substring.substring(1);
    List<String> resList = new LinkedList<>();

    if (tempString.equals("")) {
      return getWordFromChildren();
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