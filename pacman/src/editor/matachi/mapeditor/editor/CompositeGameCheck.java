package src.editor.matachi.mapeditor.editor;

/**
 * Abstract class for composite game check, combining multiple game check leaf classes into one container class
 *   [Tue 09:00] Team 03
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.util.ArrayList;
import java.util.Collections;


public abstract class CompositeGameCheck implements GameCheck {
    private ArrayList<GameCheck> gameChecks = new ArrayList<>();
    private final ArrayList<String> formattedFiles = new ArrayList<>();

    /**
     * Function to add a single `gameCheck` into the arraylist of `gameChecks`
     * @param gameCheck GameCheck which we want to add to the composite
     */
    public void addGameCheck(GameCheck gameCheck){
        gameChecks.add(gameCheck);
    }

    /**
     * Function to perform game checks with each `GameCheck`. Sorts `formattedFiles` if all files pass the game checks.
     * @param gameFiles files in the game folder
     * @param directoryName folder name
     * @return true if the folder passes all `gameCheck`s.
     */
    public boolean checkGame(ArrayList<String> gameFiles, String directoryName){
        extractFormattedFiles(gameFiles);
        for(GameCheck gameCheck : gameChecks){
            if (!gameCheck.checkGame(getFormattedFiles(), directoryName)){
                return false;
            }
        }
        Collections.sort(getFormattedFiles(), (f1, f2) -> getNumericPrefix(f1).compareTo(getNumericPrefix(f2)));
        return true;
    }

    /**
     * Function to extract files with the valid format (longer than 4 characters, start with a digit, and end with
     * ".xml")
     * @param gameFiles File name of the files in the folder.
     */
    private void extractFormattedFiles(ArrayList<String> gameFiles){
        for (String str : gameFiles){
            if (str.length() > 4 && Character.isDigit(str.charAt(0)) && str.endsWith(".xml")){
                getFormattedFiles().add(str);
            }
        }
    }

    public ArrayList<String> getFormattedFiles() {
        return formattedFiles;
    }
}
