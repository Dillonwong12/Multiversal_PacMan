package src.editor.matachi.mapeditor.editor;
import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;

import org.jdom.JDOMException;
import src.game.Game;
import src.game.GameTesterFacade;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FolderStartingStrategy implements StartingStrategy {

    @Override
    public void startEditor(String[] argsEditor) throws Exception {
        GameTesterFacade gameTesterFacade = new GameTesterFacade();
        gameTesterFacade.testGame(argsEditor);
    }
}


