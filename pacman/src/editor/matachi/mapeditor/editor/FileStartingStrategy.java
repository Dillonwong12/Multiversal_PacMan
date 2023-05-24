package src.editor.matachi.mapeditor.editor;
import java.io.File;

public class FileStartingStrategy implements StartingStrategy{
    @Override
    public void startEditor(String[] argsEditor) {
        Controller controller = Controller.getInstance();
        File file = new File(argsEditor[0]);
        controller.loadSelectedFile(file);
    }
}
