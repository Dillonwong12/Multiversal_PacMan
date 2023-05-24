package src.editor.matachi.mapeditor.editor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static Logger instance;
    private String logFilePath = "EditorLog.txt";
    private FileWriter fileWriter = null;

    public Logger(){
        try {
            fileWriter = new FileWriter(new File(logFilePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
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

//    public LoggerStrategy getLoggerStrategy(String fileOrFolderName) {
//        LoggerStrategy loggerStrategy = null;
//        // FileLogger
//        if (fileOrFolderName.endsWith(".xml")){
//
//        }
//
//        return loggerStrategy;
//    }
}
