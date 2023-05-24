package src.editor.matachi.mapeditor.editor;

import java.util.ArrayList;
import java.util.Collections;

public class CompositeGameCheck implements GameCheckStrategy {
    private ArrayList<GameCheckStrategy> gameChecks = new ArrayList<>();
    private final ArrayList<String> formattedFiles = new ArrayList<>();

    public void addGameCheck(GameCheckStrategy gameCheckStrategy){
        gameChecks.add(gameCheckStrategy);
    }

    public boolean checkGame(ArrayList<String> gameFiles, String directoryName){
        extractFormattedFiles(gameFiles);
        for(GameCheckStrategy gameCheckStrategy : gameChecks){
            if (!gameCheckStrategy.checkGame(getFormattedFiles(), directoryName)){
                return false;
            }
        }
        Collections.sort(getFormattedFiles(), (f1, f2) -> getNumericPrefix(f1).compareTo(getNumericPrefix(f2)));
        return true;
    }

    private void extractFormattedFiles(ArrayList<String> gameFiles){
        for (String str : gameFiles){
            if (str.length() > 5 && Character.isDigit(str.charAt(0)) && str.endsWith(".xml")){
                getFormattedFiles().add(str);
            }
        }
    }

    public ArrayList<String> getFormattedFiles() {
        return formattedFiles;
    }
}
