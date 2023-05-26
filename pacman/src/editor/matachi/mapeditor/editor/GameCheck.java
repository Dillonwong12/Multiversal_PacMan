package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.util.ArrayList;

/**
 * Interface defining singular gameChecks
 */
public interface GameCheck {
    /**
     * Get the level number, based on the filename, we get the character in the filename that is a digit
     * @param filename filename of the file being tested.
     * @return returns the numeric prefix of the map can be double digits.
     */
    default String getNumericPrefix(String filename) {
        StringBuilder numericPrefix = new StringBuilder();

        for (char c : filename.toCharArray()) {
            if (Character.isDigit(c)) {
                numericPrefix.append(c);
            } else {
                break;
            }
        }

        return numericPrefix.toString();
    }

    /**
     * Abstract function to check whether a game passes all the game checks.
     * @param formattedFiles Arraylist of  valid file names in the folder
     * @param directoryName Folder path
     * @return
     */
    boolean checkGame(ArrayList<String> formattedFiles, String directoryName);

}
