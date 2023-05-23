package src.editor.matachi.mapeditor.editor;

import java.util.Arrays;

public class EditorStartingStrategyFactory {
    private static EditorStartingStrategyFactory instance;

    public static EditorStartingStrategyFactory getInstance() {
        if (instance == null) {
            instance = new EditorStartingStrategyFactory();
        }
        return instance;
    }

    public StartingStrategy getStartingStrategy(String[] argsEditor) {
        StartingStrategy startingStrategy = null;
        int argNum = argsEditor.length;
        if (argNum > 1){
            System.out.println("Incorrect usage. Pass in a single file or folder as a command-line argument. Starting in Edit mode");
            startingStrategy = new EditStartingStrategy();
        }
        else if (argNum == 1){
            if (argsEditor[0].endsWith(".xml")) {
                startingStrategy = new FileStartingStrategy();
            }
            else {
                startingStrategy = new FolderStartingStrategy();
            }
        }
        else {
            startingStrategy = new EditStartingStrategy();
        }
        return startingStrategy;
    }
}
