package src.editor.matachi.mapeditor.editor;

/**
 * Interface for the `StartingStrategies`, defining how to deal with different command-line arguments.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import org.jdom.JDOMException;

import java.io.IOException;

public interface StartingStrategy {

    /**
     * Abstract method to start the Editor in different modes depending on the command-line arguments `argsEditor`.
     * @param argsEditor Command-line arguments
     * @throws Exception
     */
    void startEditor(String[] argsEditor) throws Exception;
}
