package src.game;

/**
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import org.jdom.JDOMException;
import src.editor.matachi.mapeditor.editor.Controller;
import src.editor.matachi.mapeditor.editor.EditorStartingStrategyFactory;
import src.editor.matachi.mapeditor.editor.StartingStrategy;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "pacman/properties/test2.properties";

    /**
     * Starting point
     * @param args the command line arguments
     */
    private static String[] argsEditor;
    public static void main(String[] args) throws Exception {
        Controller.getInstance().setProperties(DEFAULT_PROPERTIES_PATH);
        EditorStartingStrategyFactory startingStrategyFactory = new EditorStartingStrategyFactory();
        argsEditor = args;
        startingStrategyFactory.getStartingStrategy(argsEditor).startEditor(argsEditor);
    }

    public String[] getArgs() {
        return argsEditor;
    }

}
