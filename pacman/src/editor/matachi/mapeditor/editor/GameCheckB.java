package src.editor.matachi.mapeditor.editor;

/**
 * Leaf class for checking games with the Composite Pattern. Checks that the sequence of map files well-defined; there
 * is only one map file named with a particular number.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameCheckB implements GameCheck {

    /**
     * Checks that the sequence of `formattedFiles` only contains one map file with any particular number
     * @param formattedFiles ArrayList of valid file names in the folder
     * @param directoryName Folder path
     * @return true if `formattedFiles` only contains one map file with any particular number
     */
    @Override
    public boolean checkGame(ArrayList<String> formattedFiles, String directoryName) {
        HashMap<String, ArrayList<String>> prefixesMap = new HashMap<>();
        // Update a HashMap with maps numeric prefixes to file names
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
        // If any entry in the HashMap has more than 1 value, the check has failed
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
