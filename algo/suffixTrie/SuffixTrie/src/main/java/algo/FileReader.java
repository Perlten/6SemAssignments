package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {

  private String path;

  public FileReader(String path) {
    this.path = path;
  }

  public String[] readFile() throws FileNotFoundException {
    String text = read();
    return sanitizeInput(text);
  }

  private String[] sanitizeInput(String str) {
    str = str.trim();
    String[] arr = str.split("\\W+");
    for(int i = 0; i < arr.length; i++){
      arr[i] = arr[i].toLowerCase();
    }

    return arr;
  }

  private String read() throws FileNotFoundException {
    StringBuffer sb = new StringBuffer();
    File file = new File(this.path);
    Scanner scanner = new Scanner(file);
    while (scanner.hasNextLine()) {
      sb.append(scanner.nextLine());
    }
    scanner.close();
    return sb.toString();
  }

}