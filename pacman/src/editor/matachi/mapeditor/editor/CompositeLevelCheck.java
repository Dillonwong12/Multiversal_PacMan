package src.editor.matachi.mapeditor.editor;

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.ArrayList;

public abstract class CompositeLevelCheck implements LevelCheckStrategy{
    private ArrayList<LevelCheckStrategy> levelChecks = new ArrayList<LevelCheckStrategy>();

    public void addLevelCheck(LevelCheckStrategy levelCheck){
        levelChecks.add(levelCheck);
    }
    @Override
    public boolean checkLevel(Grid currentModel)  {
        for(LevelCheckStrategy levelCheck : levelChecks){
            if(!levelCheck.checkLevel(currentModel)){
                return false;
            }
        }
        return true;
    }
}
