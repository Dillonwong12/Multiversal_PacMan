package src.editor.matachi.mapeditor.editor;

import org.jdom.JDOMException;
import src.editor.matachi.mapeditor.grid.GridModel;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.List;

// Check that items are accessible relative to pacman starting location
public class LevelCheckD implements LevelCheckStrategy{

    @Override
    public boolean checkLevel(GridModel currentModel) {
        return false;
    }
}
