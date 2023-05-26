package src.editor.matachi.mapeditor.editor;

/**
 * Interface for the Composite Pattern for `GameCheck`s
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.util.ArrayList;

public interface GameCheck {

    /**
     * Extract the numeric prefix of a `filename`
     * @param filename File name of the file being tested.
     * @return The numeric prefix of the file (can be multiple digits)
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
     * Checks whether a game passes `GameChecks`
     * @param formattedFiles ArrayList of valid file names in the folder
     * @param directoryName Folder path
     * @return true if the `formattedFiles` pass the `GameCheck`(s)
     */
    boolean checkGame(ArrayList<String> formattedFiles, String directoryName);

}
