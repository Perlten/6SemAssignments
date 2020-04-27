package algo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonObject;

public class Utils {

  public static List<JsonObject> readCsv(String path) throws FileNotFoundException {
  List<JsonObject> resList = new ArrayList<>();
  File file = new File(path);
  Scanner scanner = new Scanner(file);
  String fields = scanner.nextLine();
  fields = fields.toLowerCase();
  
  String[] fieldArr = fields.split(";");
  
  while(scanner.hasNextLine()){
    JsonObject object = new JsonObject();
    String[] line = scanner.nextLine().split(";");
    for(int i = 0; i < fieldArr.length; i++){
      String value = line.length <= i ? null: line[i];
      object.addProperty(fieldArr[i], value);
    }
    resList.add(object);
  }

  scanner.close();
  return resList;
} 

}