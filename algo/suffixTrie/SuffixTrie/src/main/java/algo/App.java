package algo;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
  public static void main(String[] args) throws FileNotFoundException {
    // String[] arr = new FileReader("./shakespeare-complete-works.txt").readFile();

    SuffixTrie sf = new SuffixTrie();
    // for (String word : arr) {
    //   sf.add(word);
    // }

    sf.add("per");
    sf.add("nikolai");
    sf.add("jesper");
    sf.add("troels");
    sf.add("perlt");
    sf.add("perit");
    sf.add("perlo");
    sf.add("pedro");
    sf.add("pedrolifiet");

    // sf.add("ananas");

    boolean run = true;

    Scanner sc = new Scanner(System.in);
    while (run) {
      System.out.println("Pleaser enter a substring of shakespears combined works. Thank you *sips tea*");
      String substring = sc.next();

      Collection<String> res = sf.find(substring);
      for (String resString : res) {
        System.out.println(resString);
      }
    }
    sc.close();

  }
}
