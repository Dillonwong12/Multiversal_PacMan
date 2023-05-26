package src.editor.matachi.mapeditor.editor;

/**
 * Leaf class for checking games with the Composite Pattern. Checks that there is at least one correctly named map
 * file in the folder.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.util.ArrayList;

public class GameCheckA implements GameCheck {
    /**
     * Checks if there exists at least one correctly named folder in `formattedFiles`
     * @param formattedFiles ArrayList of valid file names in the folder
     * @param directoryName Folder path
     * @return true if there exists one correctly named folder and false otherwise
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
