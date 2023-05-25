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
public class GameDriver {
    public static void main(String[] args) throws IOException, JDOMException {
        GameCallback gameCallback = new GameCallback();

        Properties properties = PropertiesLoader.loadPropertiesFile(args[0]);
        File file = new File(args[1]);

        Game game = new Game(gameCallback, properties, file);

        // Check if args[2] is not null, and send a result of the Game to the Editor
        if(args[2].equals("true")){

            Socket socket = new Socket("localhost", 65000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            boolean value = game.isGameWon();

            out.writeBoolean(value);

            socket.close();
        }
        System.exit(0);
    }
}
