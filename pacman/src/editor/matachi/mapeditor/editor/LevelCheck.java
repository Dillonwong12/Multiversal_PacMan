package src.editor.matachi.mapeditor.editor;

import src.editor.matachi.mapeditor.grid.Grid;


public interface LevelCheck {
    // Function which checks if the level is valid based on their own rules
    boolean checkLevel(Grid currentModel, String fileName);
}
