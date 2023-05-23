package src.editor.matachi.mapeditor.editor;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import src.editor.matachi.mapeditor.grid.GridModel;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;


public interface LevelCheckStrategy {
    // Function which checks if the level is valid based on their own rules
    public abstract boolean checkLevel(GridModel currentModel);


}
