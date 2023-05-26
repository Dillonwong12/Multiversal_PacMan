package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import src.editor.matachi.mapeditor.grid.Grid;

/**
 * Interface for singular level checks to implement
 */
public interface LevelCheck {
    // Function which checks if the level is valid based on their own rules
    boolean checkLevel(Grid currentModel, String fileName);
}
