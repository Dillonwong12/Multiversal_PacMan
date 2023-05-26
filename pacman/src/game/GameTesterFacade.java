package src.game;

import src.editor.matachi.mapeditor.editor.*;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class GameTesterFacade {
    public GameTesterFacade(){
    }

    public void testGame(String[] args) throws Exception{
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
            int i = 0;
            while (i < compositeABGameCheck.getFormattedFiles().size()) {
                File selectedFile = null;
                boolean finished = false;
                boolean won = false;
                for (File file: files){
                    if (file.getName().equals(compositeABGameCheck.getFormattedFiles().get(i))){
                        selectedFile = file;
                        break;
                    }
                }
                if (selectedFile != null) {
                    Controller.getInstance().loadSelectedFile(selectedFile);
                    if (!compositeABCDLevelCheck.checkLevel(Controller.getInstance().getModel(), selectedFile.getName())) {
                        System.out.println(selectedFile.getName() + " error: please refer to log file.");
                        break;
                    }

                    String filePath = selectedFile.getPath();
                    String pathSeparator = System.getProperty("path.separator");
                    String classPath = "out" + pathSeparator + "out/lib/jdom-1.1.3.jar" + pathSeparator + "out/lib/JGameGrid.jar";
                    String mainClass = "src.game.GameDriver";

                    ProcessBuilder process = new ProcessBuilder("java", "-cp", classPath, mainClass, Controller.getInstance().getPropertiesPath(), filePath, "true");
                    process.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    process.redirectError(ProcessBuilder.Redirect.INHERIT);

                    try {
                        Process p = process.start();
                        ServerSocket serverSocket = new ServerSocket(65000);
                        Socket socket = serverSocket.accept();
                        DataInputStream in = new DataInputStream(socket.getInputStream());

                        // get from game socket
                        won = in.readBoolean();
                        socket.close();
                        serverSocket.close();
                        // if we win
                        if (won && i < compositeABGameCheck.getFormattedFiles().size()-1) {
                            i++;
                        }
                        else{
                            Controller.getInstance().resetEditor();
                            System.out.println("Game over");

                            break;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
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
