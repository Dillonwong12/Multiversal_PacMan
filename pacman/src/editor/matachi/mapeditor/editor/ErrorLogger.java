package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Singleton Class for error logger, all error messages outputed to error logger.
 */
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
     * global access point for error logger
     * @return Singleton Error logger
     */
    public static ErrorLogger getInstance() {
        if (instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }

    /**
     * Function to write a given input string to the error logger followed by a null character.
     * @param str String that should be outputted to error logger.
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
