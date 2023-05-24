package src.editor.matachi.mapeditor.editor;
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

    public static ErrorLogger getInstance() {
        if (instance == null) {
            instance = new ErrorLogger();
        }
        return instance;
    }

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
