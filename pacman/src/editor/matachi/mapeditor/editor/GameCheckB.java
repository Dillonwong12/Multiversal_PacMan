package src.editor.matachi.mapeditor.editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameCheckB implements GameCheck {
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
