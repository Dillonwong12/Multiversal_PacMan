package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singular game check B
 * Game check B: checks that the sequence of maps are valid, only one map file
 * with a particular number.
 */
public class GameCheckB implements GameCheck {
    /**
     * Function to check whether a game passes gameCheck B
     * @param formattedFiles Arraylist of valid files in the folder
     * @param directoryName Folder path
     * @return returns true if all valid files passes check B and false if any doesn't.
     */
    @Override
    public boolean checkGame(ArrayList<String> formattedFiles, String directoryName) {
        HashMap<String, ArrayList<String>> prefixesMap = new HashMap<>();
        for (String file: formattedFiles){
            String numericPrefix = getNumericPrefix(file);
            if (prefixesMap.containsKey(numericPrefix)){
                prefixesMap.get(numericPrefix).add(file);
            }
            else{
                ArrayList<String> fileNames = new ArrayList<>();
                prefixesMap.put(numericPrefix, fileNames);
                prefixesMap.get(numericPrefix).add(file);
            }
        }
        for (Map.Entry<String, ArrayList<String>> entry : prefixesMap.entrySet()){
            if (entry.getValue().size() > 1){
                String invalidMaps = "";
                for (String str : entry.getValue()){
                    invalidMaps += str+"; ";
                }
                String invalidMapsStr = invalidMaps.substring(0, invalidMaps.length()-2);
                ErrorLogger.getInstance().writeString(String.format("[Game %s – multiple maps at same level: %s]", directoryName, invalidMapsStr));
                return false;
            }
        }

        return true;
    }
}
