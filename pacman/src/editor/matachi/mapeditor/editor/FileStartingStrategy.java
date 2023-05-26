package src.editor.matachi.mapeditor.editor;
import java.io.File;

/**
 * Editor starting strategy for when a single XML file is given as an input
 */
public class FileStartingStrategy implements StartingStrategy{
    /**
     * Starting strategy for when a single XML file is given as an input
     * Opens the editor and loads it with the input XML file
     * @param argsEditor Input Command Line Arguments.
     */
    @Override
    public void startEditor(String[] argsEditor) {
        Controller controller = Controller.getInstance();
        File file = new File(argsEditor[0]);
        controller.loadSelectedFile(file);
    }
}
