package src.editor.matachi.mapeditor.editor;
import java.io.File;
import java.util.ArrayList;

import org.jdom.JDOMException;
import src.game.Game;
import src.game.utility.GameCallback;
import src.game.utility.PropertiesLoader;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class FolderStartingStrategy implements StartingStrategy{

    @Override
    public void startEditor(String[] argsEditor) {
        Controller controller = new Controller();
        ArrayList<String> gameFiles = new ArrayList<>();
        CompositeGameCheck compositeABGameCheck = new CompositeABGameCheck();
        try {
            File directoryPath = new File(argsEditor[0]);
            File files[] = directoryPath.listFiles();
            for (int i=0; i<files.length; i++) {
                gameFiles.add(files[i].getName());
            }
            if (compositeABGameCheck.checkGame(gameFiles, directoryPath.getName())){
                System.out.println(compositeABGameCheck.getFormattedFiles());
            }
            else {
                System.out.println("check failed");
                System.out.println(compositeABGameCheck.getFormattedFiles());
            }
        }
        catch (Exception e){
            e.printStackTrace();
    public void startEditor(String[] argsEditor) throws IOException, JDOMException {
        File folder = new File(argsEditor[0]);
        System.out.println("Folder: " + folder.getAbsolutePath());
        // GAME CHECK GOES HERE

        // get array of files inside the folder
        File[] files= folder.listFiles();

        for(File file:files){
            // if game check passes play each level
            Game game =  new Game(new GameCallback(), Controller.getInstance().getProperties(), file);
            // check if we won or lost the game
            if(!game.isGameWon()){
                // go to edit mode if lost
                Controller controller = Controller.getInstance();
                break;

            }
        }

    }

}
