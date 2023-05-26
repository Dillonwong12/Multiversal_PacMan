package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.util.Arrays;

/**
 * Factory pattern class containing logic how to deal with arguments
 * Calls the appropriate file opening strategy depending on argument
 */
public class EditorStartingStrategyFactory {
    /**
     * Function to get the appropriate strategy given the input command line argument
     * @param argsEditor command line arguments arg
     * @return returns the Appropriate starting strategy to be used given the input
     */
    public StartingStrategy getStartingStrategy(String[] argsEditor) {
        StartingStrategy startingStrategy = null;
        int argNum = argsEditor.length;
        // more than 1 argument in command line argument
        if (argNum > 1){
            System.out.println("Incorrect usage. Pass in a single file or folder as a command-line argument. Starting in Edit mode");
            startingStrategy = new EditStartingStrategy();
        }
        // if singular file
        else if (argNum == 1){
            if (argsEditor[0].endsWith(".xml")) {
                startingStrategy = new FileStartingStrategy();
            }
            // if folder
            else {
                startingStrategy = new FolderStartingStrategy();
            }
        }
        // if empty / no arguments
        else {
            startingStrategy = new EditStartingStrategy();
        }
        return startingStrategy;
    }
}
