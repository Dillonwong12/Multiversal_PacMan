package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.util.ArrayList;

/**
 * Singular GameCheck A testing,
 * Game Check A: checks that there exit at least 1 correctly named folder.
 */
public class GameCheckA implements GameCheck {
    /**
     *
     * @param formattedFiles Arraylist of files in the folder that have a valid name
     * @param directoryName Folder path
     * @return true if there exist 1 correctly named folder and false if there are none
     */
    @Override
    public boolean checkGame(ArrayList<String> formattedFiles, String directoryName) {
        if (formattedFiles.size() == 0){
            ErrorLogger.getInstance().writeString(String.format("[Game %s – no maps found]", directoryName));
            return false;
        }
        return true;
    }
}
