package src.editor.matachi.mapeditor.editor;
import java.io.File;
import java.util.ArrayList;

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
        }

    }
}
