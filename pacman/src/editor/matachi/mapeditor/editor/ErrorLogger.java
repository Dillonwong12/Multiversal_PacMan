package src.editor.matachi.mapeditor.editor;

/**
 * Singleton class for logging errors from failed `LevelCheck`s and `GameCheck`s.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class ErrorLogger {
    private static ErrorLogger instance;
    private String logFilePath = "EditorErrorLog.txt";
    private FileWriter fileWriter = null;

    public ErrorLogger(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Global access point for ErrorLogger
     * @return Singleton ErrorLogger
     */
    public static ErrorLogger getInstance() {
        if (instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }

    /**
     * Writes a given input string `str` to a file entitled `logFilePath`
     * @param str String that should be logged
     */
    public void writeString(String str) {
        try {
            fileWriter.write(str);
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
