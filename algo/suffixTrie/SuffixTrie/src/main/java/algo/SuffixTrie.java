package algo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SuffixTrie {

  private List<RootNode> rootNodes = new LinkedList<>();

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
      for (RootNode rootNode : rootNodes) {
        if (rootNode.getLetter() == letter) {
          rootNode.addChild(substring);
          found = true;
        }
      }

      if (!found) {
        RootNode newRootNode = new RootNode(letter);
        this.rootNodes.add(newRootNode);
        newRootNode.addChild(substring);
      }
    }
  }

  public Collection<String> find(String substring) {
    char rootLetter = substring.charAt(0);
    String tempSubstring = substring.substring(1);
    for (RootNode rootNode : rootNodes) {
      if(rootNode.getLetter() == rootLetter){
       return rootNode.find(tempSubstring);
      }
    }
    return new LinkedList<>();
  }
}