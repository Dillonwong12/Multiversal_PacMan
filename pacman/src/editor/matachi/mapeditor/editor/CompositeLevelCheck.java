package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.ArrayList;

/**
 * Abstract class for composite level check, contains an arraylist of singular levelChecks
 */
public abstract class CompositeLevelCheck implements LevelCheck {
    private ArrayList<LevelCheck> levelChecks = new ArrayList<LevelCheck>();

    /**
     * Function to add a singular Level check to the array list of levelChecks
     * @param levelCheck Level check that we want to add to the composite
     */
    public void addLevelCheck(LevelCheck levelCheck){
        levelChecks.add(levelCheck);
    }

    /**
     * Fuction that iterates checks whether a given map fulfills all the level check
     * @param currentModel current map/grid
     * @param fileName filenaem containing the current map/grid
     * @return true if the file passes all levelChecks and false if it fails any check
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
