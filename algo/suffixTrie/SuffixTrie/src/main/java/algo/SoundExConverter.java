package algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SoundExConverter {

  private static List<Character> forbiddenChars = new ArrayList<>(
      Arrays.asList(new Character[] { 'a', 'e', 'i', 'o', 'u', 'y', 'h', 'w' }));

  public static String convertStringToSoundex(String word) {
    String cleanWord = "";
    String soundEx = "";
    soundEx += word.charAt(0);
    word = word.substring(1);

    for (int i = 0; i < word.length(); i++) {
      char currentLetter = word.charAt(i);
      if (!forbiddenChars.contains(currentLetter)) {
        cleanWord += currentLetter;
      }
    }

    for (int i = 0; i < 3; i++) {
      try {
        soundEx += getCharValue(cleanWord.charAt(i));
      } catch (StringIndexOutOfBoundsException e) {
        soundEx += "0";
      }
    }
    return soundEx;
  }

  private static int getCharValue(char letter) {
    switch (letter) {
      case 'b':
        return 1;
      case 'f':
        return 1;
      case 'p':
        return 1;
      case 'v':
        return 1;
      case 'c':
        return 2;
      case 'g':
        return 2;
      case 'j':
        return 2;
      case 'k':
        return 2;
      case 'q':
        return 2;
      case 's':
        return 2;
      case 'x':
        return 2;
      case 'z':
        return 2;
      case 'd':
        return 3;
      case 't':
        return 3;
      case 'l':
        return 4;
      case 'n':
        return 5;
      case 'm':
        return 5;
      case 'r':
        return 6;
    }
    return 0;
  }

  public static void main(String[] args) {
    System.out.println(convertStringToSoundex("perlt"));
  }

}