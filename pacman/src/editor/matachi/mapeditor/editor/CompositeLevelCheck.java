package src.editor.matachi.mapeditor.editor;

import org.jdom.JDOMException;
import src.editor.matachi.mapeditor.grid.GridModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public abstract class CompositeLevelCheck implements LevelCheckStrategy{
    private ArrayList<LevelCheckStrategy> levelChecks = new ArrayList<LevelCheckStrategy>();

    public void addLevelCheck(LevelCheckStrategy levelCheck){
        levelChecks.add(levelCheck);
    }
    @Override
    public boolean checkLevel(GridModel currentModel)  {
        for(LevelCheckStrategy levelCheck : levelChecks){
            if(!levelCheck.checkLevel(currentModel)){
                return false;
            }
        }
        return true;
    }
}
