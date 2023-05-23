package src.game;

import src.editor.matachi.mapeditor.editor.Controller;
import src.editor.matachi.mapeditor.editor.EditorStartingStrategyFactory;
import src.editor.matachi.mapeditor.editor.StartingStrategy;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.util.Properties;

public class Driver {
    public static final String DEFAULT_PROPERTIES_PATH = "pacman/properties/test2.properties";

    /**
     * Starting point
     * @param args the command line arguments
     */
    private static String[] argsEditor;
    public static void main(String[] args) {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        argsEditor = args;
        EditorStartingStrategyFactory.getInstance().getStartingStrategy(argsEditor).startEditor(argsEditor);
    }

    public String[] getArgs() {
        return argsEditor;
    }

}
