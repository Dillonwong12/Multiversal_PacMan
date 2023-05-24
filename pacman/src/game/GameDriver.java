package src.game;

import org.jdom.JDOMException;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.net.ServerSocket;
import java.net.Socket;
// pacman/properties/test2.properties
// pacman/testFolder/2sample_map.xml
public class GameDriver {
    public static void main(String[] args) throws IOException, JDOMException {
        System.out.println("driver hello how low");
        GameCallback gameCallback = new GameCallback();

        Properties properties = PropertiesLoader.loadPropertiesFile(args[0]);
        File file = new File(args[1]);

        Game game = new Game(gameCallback, properties, file);

        // check if args[2] is not null as in if folder starting strategy called it
        if(args[2] != null){
            Socket socket = new Socket("localhost", 8000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            boolean value = game.isGameWon();

            out.writeBoolean(value);

            socket.close();
        }
        System.exit(0);
    }
}
