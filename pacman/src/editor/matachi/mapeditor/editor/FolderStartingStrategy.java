package src.editor.matachi.mapeditor.editor;
import java.io.DataInputStream;
import java.io.File;
import java.util.ArrayList;

import org.jdom.JDOMException;
import src.game.Game;
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
    public void startEditor(String[] argsEditor) throws IOException, JDOMException {
        Controller controller = Controller.getInstance();
        ArrayList<String> gameFiles = new ArrayList<>();
        CompositeGameCheck compositeABGameCheck = new CompositeABGameCheck();
        CompositeLevelCheck compositeABCDLevelCheck = new CompositeABCDLevelCheck();
        try {
            File directoryPath = new File(argsEditor[0]);
            File files[] = directoryPath.listFiles();
            for (int i = 0; i < files.length; i++) {
                gameFiles.add(files[i].getName());
            }
            // Game check
            if (compositeABGameCheck.checkGame(gameFiles, directoryPath.getName())) {
                System.out.println(compositeABGameCheck.getFormattedFiles());
            } else {
                System.out.println("check failed");
                System.out.println(compositeABGameCheck.getFormattedFiles());
                return;
            }
            int i = 0;
            while (i < files.length) {
                boolean finished = false;
                boolean won = false;
                if (compositeABGameCheck.getFormattedFiles().contains(files[i].getName())) {
                    Controller.getInstance().loadSelectedFile(files[i]);
                    if (!compositeABCDLevelCheck.checkLevel(Controller.getInstance().getModel(), files[i].getName())) {
                        System.out.println(files[i].getName() + " error: please refer to log file.");
                        break;
                    }

                    String filePath = files[i].getPath();
                    String classPath = "out:out/lib/jdom-1.1.3.jar:out/lib/JGameGrid.jar";
                    String mainClass = "src.game.GameDriver";

                    ProcessBuilder process = new ProcessBuilder("java", "-cp", classPath, mainClass, Controller.getInstance().getPropertiesPath(), filePath, "true");
                    process.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    process.redirectError(ProcessBuilder.Redirect.INHERIT);

                    try {
                        Process p = process.start();
                        ServerSocket serverSocket = new ServerSocket(8000);
                        Socket socket = serverSocket.accept();
                        DataInputStream in = new DataInputStream(socket.getInputStream());

                        // get from game socket
                        won = in.readBoolean();
                        System.out.println("won: " + won);
                        socket.close();
                        serverSocket.close();
                        // if we win
                        if (won) {
                            i++;
                            continue;
                        }
                        else{
                            Controller.getInstance().resetEditor();
                            Controller.getInstance();
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
        } finally {

        }
    }
}


