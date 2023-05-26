package src.editor.matachi.mapeditor.editor;

/**
 * Abstract class for composite level check, combining multiple level check leaf classes into one container class
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.ArrayList;

public abstract class CompositeLevelCheck implements LevelCheck {
    private ArrayList<LevelCheck> levelChecks = new ArrayList<LevelCheck>();

    /**
     * Function to add a singular `levelCheck` to the `levelChecks`
     * @param levelCheck `LevelCheck` that we want to add to the composite
     */
    public void addLevelCheck(LevelCheck levelCheck){
        levelChecks.add(levelCheck);
    }

    /**
     * Function to perform level checks with each `LevelCheck`
     * @param currentModel current map/grid
     * @param fileName filename containing the current map/grid
     * @return true if the file passes all `LevelCheck`s and false if it fails any check
     */
    @Override
    public boolean checkLevel(Grid currentModel, String fileName)  {
        for(LevelCheck levelCheck : levelChecks){
            if(!levelCheck.checkLevel(currentModel, fileName)){
                return false;
            }
        }
        return true;
    }
}
