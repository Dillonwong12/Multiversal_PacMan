package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.util.ArrayList;
import java.util.Collections;

/**
 * Abstract class for composite game check, combining multiple game check rules into 1
 */
public abstract class CompositeGameCheck implements GameCheck {
    private ArrayList<GameCheck> gameChecks = new ArrayList<>();
    private final ArrayList<String> formattedFiles = new ArrayList<>();

    /**
     * Function to add a single gameCheck into the arraylist of gameCHecks
     * @param gameCheck gameCheck which we want to add.
     */
    public void addGameCheck(GameCheck gameCheck){
        gameChecks.add(gameCheck);
    }

    /**
     * Function to check each Game File with each checkGame.
     * Sorts the game files if all files pass each game Check
     * @param gameFiles files in the game folder
     * @param directoryName folder name
     * @return Returns true and sorts the File Array if the folder passes each and every gameCheck.
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
     * Function to extract files where the length of the file name is > 5, has a digit as its first character,
     * and ends with xml, valid files.
     * @param gameFiles File name of the files in the folder.
     */
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
