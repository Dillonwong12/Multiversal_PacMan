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
            System.out.println(Arrays.asList(files));
            for (int i=0; i<files.length; i++) {
                gameFiles.add(files[i].getName());
            }
            // Game check
            if (compositeABGameCheck.checkGame(gameFiles, directoryPath.getName())){
                System.out.println(compositeABGameCheck.getFormattedFiles());
            }
            else {
                System.out.println("check failed");
                System.out.println(compositeABGameCheck.getFormattedFiles());
                return;
            }

            for (File file: files){
                if(compositeABGameCheck.getFormattedFiles().contains(file.getName())){
                    Controller.getInstance().loadSelectedFile(file);
                    if (!compositeABCDLevelCheck.checkLevel(Controller.getInstance().getModel(), file.getName())){
                        System.out.println(file.getName() + " error: please refer to log file.");
                        break;
                    }
                    Game game =  new Game(new GameCallback(), Controller.getInstance().getProperties(), file);
                    // check if we won or lost the game
                    if(!game.isGameWon()){
                        // go to edit mode if lost
                        controller = Controller.getInstance();
                        break;

                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


