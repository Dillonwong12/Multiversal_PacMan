package src.editor.matachi.mapeditor.editor;

import java.util.ArrayList;

public interface GameCheck {

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

    boolean checkGame(ArrayList<String> formattedFiles, String directoryName);

}
