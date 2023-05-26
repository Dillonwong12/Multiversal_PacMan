package src.editor.matachi.mapeditor.editor;

/**
 * Interface for the Composite Pattern for `LevelCheck`s
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import src.editor.matachi.mapeditor.grid.Grid;

public interface LevelCheck {

    /**
     * Checks whether a map with `fileName` passes `LevelChecks`
     * @param currentModel Grid representation of the map
     * @param fileName File name
     * @return true if the `currentModel` passes the `LevelCheck`(s)
     */
    boolean checkLevel(Grid currentModel, String fileName);
}
