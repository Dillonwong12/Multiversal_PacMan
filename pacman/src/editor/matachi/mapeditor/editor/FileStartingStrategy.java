package src.editor.matachi.mapeditor.editor;
import java.io.File;

/**
 * `StartingStrategy` which opens the Editor to Map Edit Mode.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

public class FileStartingStrategy implements StartingStrategy{
    /**
     * Starting strategy for when a single XML file is given as command-line input
     * Opens the editor and loads it with the input XML file
     * @param argsEditor The command-line arguments
     */
    @Override
    public void startEditor(String[] argsEditor) {
        Controller controller = Controller.getInstance();
        File file = new File(argsEditor[0]);
        controller.loadSelectedFile(file);
    }
}
