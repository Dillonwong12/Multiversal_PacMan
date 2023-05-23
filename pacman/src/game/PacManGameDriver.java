package src.game;

import org.jdom.JDOMException;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.io.IOException;
import java.util.Properties;

public class PacManGameDriver {
    public static final String DEFAULT_PROPERTIES_PATH = "pacman/properties/test2.properties";

    /**
     * Starting point
     * @param args the command line arguments
     */


    private void startGame(String[] args) throws IOException, JDOMException {
        String propertiesPath = DEFAULT_PROPERTIES_PATH;
        if (args.length > 0) {
            propertiesPath = args[0];
        }
        final Properties properties = PropertiesLoader.loadPropertiesFile(propertiesPath);
        GameCallback gameCallback = new GameCallback();
        new Game(gameCallback, properties);
    }

//    public static void getStartGame(String[] args){
//        startGame(args);
//    }
}