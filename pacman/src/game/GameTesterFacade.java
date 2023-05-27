package src.game;

/**
 * Facade to perform game tests when receiving a folder as command-line input.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import src.editor.matachi.mapeditor.editor.*;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Properties;

public class GameTesterFacade  {
    private boolean gameResult;

    public GameTesterFacade(){
    }

    /**
     * Performs a game test
     * @param args The command-line arguments
     * @throws Exception
     */
    public void testGame(String[] args) throws Exception {
        ArrayList<String> gameFiles = new ArrayList<>();
        CompositeGameCheck compositeABGameCheck = new CompositeABGameCheck();
        CompositeLevelCheck compositeABCDLevelCheck = new CompositeABCDLevelCheck();
        try {
            File directoryPath = new File(args[0]);
            File[] files = directoryPath.listFiles();
            for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
                gameFiles.add(files[i].getName());
            }

            // Game check
            if (!compositeABGameCheck.checkGame(gameFiles, directoryPath.getName())) {
                return;
            }

            // Iterate through the game files
            int i = 0;
            while (i < compositeABGameCheck.getFormattedFiles().size()) {
                File selectedFile = null;

                for (File file : files){
                    if (file.getName().equals(compositeABGameCheck.getFormattedFiles().get(i))){
                        selectedFile = file;
                        break;
                    }
                }
                if (selectedFile != null) {
                    Controller.getInstance().loadSelectedFile(selectedFile);

                    // Level check
                    if (!compositeABCDLevelCheck.checkLevel(Controller.getInstance().getModel(), selectedFile.getName())) {
                        break;
                    }

                    // Run the game
                    final File finalSelectedFile = selectedFile;
                    Game gameBuffer;
                    SwingWorker<Boolean, Void> gameWorker = new SwingWorker<Boolean, Void>() {
                        @Override
                        protected Boolean doInBackground() throws Exception {
                            GameCallback callback = new GameCallback();
                            Game game = new Game(callback, Controller.getInstance().getProperties(), finalSelectedFile);
                            Boolean result = game.isGameWon();


                            return result;
                        }
                    };

                    gameWorker.execute();
                    gameResult = gameWorker.get();

                    // If we win
                    if (gameResult && i < compositeABGameCheck.getFormattedFiles().size()-1) {
                        i++;
                    }
                    else{
                        Controller.getInstance().resetEditor();
                        break;
                    }
                }
            }
            // Return to edit mode with no current map
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
