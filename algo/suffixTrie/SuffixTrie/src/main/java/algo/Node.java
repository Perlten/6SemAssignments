package algo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

class Node {
  private char letter;
  private List<Node> children = new LinkedList<>();
  private String word;
  private String wordSubstring;
  private int layer;
  private List<String> finalStrings = new LinkedList<>();

  public Node(char letter, int layer) {
    this.letter = letter;
    this.layer = layer;
  }

  /**
   * @param substring
   * @param wholeWord Adds a child to our node
   */
  public void addChild(String substring, String wholeWord) {
    substring = substring.substring(1);

    // orginal nåede sit maks men nye ord stadig skal lave en ny node
    if (substring == null || substring.equals("")) {
      if (this.wordSubstring != null && this.wordSubstring.equals("")) {
        this.finalStrings.add(wholeWord);
        return;
      }
      // hvis der ligger et ord der er større end det nye så smider vi det større
      // afsted til en ny node
      if (this.word != null && this.word != wholeWord) {
        String orginalWord = this.word;
        String orginalWordSubstring = this.wordSubstring;
        char orginalLetter = orginalWordSubstring.charAt(0);
        Node node = new Node(orginalLetter, layer + 1);
        this.children.add(node);
        node.addChild(orginalWordSubstring, orginalWord);
      }

      this.word = wholeWord;
      this.wordSubstring = substring;
      return;
    }

    // hvis noden er tom bruger vi compact
    if (this.word == null && this.children.size() == 0) {
      this.word = wholeWord;
      this.wordSubstring = substring;
      return;
    }

    char substringLetter = substring.charAt(0);

    // Leder efter en child node der passer til letter
    for (Node node : this.children) {
      if (node.getLetter() == substringLetter) {
        node.addChild(substring, wholeWord);
        return;
      }
    }

    // if orginal word is in the right node only create a new node for new word
    if (this.wordSubstring == null || this.wordSubstring.equals("")) {
      Node newNode = new Node(substringLetter, layer + 1);
      newNode.addChild(substring, wholeWord);
      this.children.add(newNode);
      return;
    }

    // her skal vi grene ud med nodes
    String orginalWord = this.word;
    String orginalWordSubstring = this.wordSubstring;
    char orginalLetter = orginalWordSubstring.charAt(0);

    // hvis de to substrings har samme letter laver vi en node
    if (orginalLetter == substringLetter) {
      Node node = new Node(orginalLetter, layer + 1);
      this.children.add(node);
      node.addChild(substring, wholeWord);
      node.addChild(orginalWordSubstring, orginalWord);
      this.word = null;
      this.wordSubstring = null;
      return;
    }
    // hvis ikke laver vi 2
    Node orginalNode = new Node(orginalLetter, layer + 1);
    orginalNode.addChild(orginalWordSubstring, orginalWord);

    Node newNode = new Node(substringLetter, layer + 1);
    newNode.addChild(substring, wholeWord);
    this.children.add(orginalNode);
    this.children.add(newNode);
    this.word = null;
    this.wordSubstring = null;

  }

  public Set<String> getWordFromChildren() {
    Set<String> resList = new HashSet<>();
    if (this.word != null) {
      resList.add(this.word);
      resList.addAll(this.finalStrings);
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
  public Set<String> find(String substring, String orginalSubstring) {
    // removes the first letter and checks if string is not empty
    String tempString = substring.substring(1);
    Set<String> resList = new HashSet<>();

    if (tempString.equals("")) {
      return getWordFromChildren();
    }

    if (this.children.size() == 0) {
      if (this.word.contains(orginalSubstring)) {
        resList.add(this.word);
      }
      for (String finalString : this.finalStrings) {
        if (finalString.contains(finalString)) {
          resList.add(finalString);
        }
      }
      return resList;
    }

    // saves the first letter
    char letter = tempString.charAt(0);

    // iterate our children list and checks if we can find a child with the given
    // letter and then call that childs find method recursively
    for (Node node : this.children) {
      if (node.getLetter() == letter) {
        return node.find(tempString, orginalSubstring);
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