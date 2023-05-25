package src.editor.matachi.mapeditor.editor;

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.ArrayList;

public abstract class CompositeLevelCheck implements LevelCheck {
    private ArrayList<LevelCheck> levelChecks = new ArrayList<LevelCheck>();

    public void addLevelCheck(LevelCheck levelCheck){
        levelChecks.add(levelCheck);
    }
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
