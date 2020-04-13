package algo;

import java.util.LinkedList;
import java.util.List;

public class SuffixTrie {

  private List<RootNode> rootNodes = new LinkedList<>();

  public static String wholeWord;

  public void add(String word) {
    word = word.toLowerCase();
    SuffixTrie.wholeWord = word;
    
    for (int i = 0; i < word.length(); i++) {
      word = word.substring(i);
      char letter = word.charAt(0);
     
      boolean found = false;
      for (RootNode rootNode : rootNodes) {
        if (rootNode.getLetter() == letter) {
          rootNode.addChild(word);
          found = true;
        }
      }

      if (!found) {
        RootNode newRootNode = new RootNode(letter);
        this.rootNodes.add(newRootNode);
        newRootNode.addChild(word);
      }
    }
  }

  public List<String> find(String substring) {
    return null;
  }
}