package src.editor.matachi.mapeditor.editor;

import java.util.ArrayList;

public class GameCheckA implements GameCheckStrategy {
    @Override
    public boolean checkGame(ArrayList<String> formattedFiles, String directoryName) {
        if (formattedFiles.size() == 0){
            System.out.println(String.format("[Game %s – no maps found]", directoryName));
            return false;
        }
        return true;
    }
}