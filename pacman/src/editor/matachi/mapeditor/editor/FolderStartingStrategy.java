package src.editor.matachi.mapeditor.editor;
import java.io.File;
import java.util.ArrayList;

import org.jdom.JDOMException;
import src.game.Game;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FolderStartingStrategy implements StartingStrategy{

    @Override
    public void startEditor(String[] argsEditor) throws IOException, JDOMException {
        Controller controller = Controller.getInstance();
        ArrayList<String> gameFiles = new ArrayList<>();
        CompositeGameCheck compositeABGameCheck = new CompositeABGameCheck();
        CompositeLevelCheck compositeABCDLevelCheck = new CompositeABCDLevelCheck();
        try {
            File directoryPath = new File(argsEditor[0]);
            File files[] = directoryPath.listFiles();
            for (int i=0; i<files.length; i++) {
                gameFiles.add(files[i].getName());
            }
            // Game check
            if (!compositeABGameCheck.checkGame(gameFiles, directoryPath.getName())){
                System.out.println("%s error: please refer to EditorErrorLog.txt");
                System.out.println(compositeABGameCheck.getFormattedFiles());
                return;
            }
            List<File> fileArr = Arrays.asList(files);
            boolean returnToEditor = false;
            for (String fileName: compositeABGameCheck.getFormattedFiles()){
                for (File file: fileArr){
                    if (file.getName().equals(fileName)){
                        Controller.getInstance().loadSelectedFile(file);
                        if (!compositeABCDLevelCheck.checkLevel(Controller.getInstance().getModel(), file.getName())){
                            System.out.println(file.getName() + " error: please refer to log file.");
                            return;
                        }
                        Game game =  new Game(new GameCallback(), Controller.getInstance().getProperties(), file);
                        // check if we won or lost the game
                        if(!game.isGameWon()){
                            // go to edit mode if lost
                            returnToEditor = true;
                            controller = Controller.getInstance();
                            break;
                        }
                        break;
                    }
                }
                if (returnToEditor){
                    // Return to edit mode with no current map
                    return;
                }
            }
            // Return to edit mode with no current map
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


