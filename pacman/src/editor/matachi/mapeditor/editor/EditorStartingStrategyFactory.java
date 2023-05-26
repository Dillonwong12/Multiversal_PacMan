package src.editor.matachi.mapeditor.editor;

/**
 * Factory for constructing `StartingStrategies` based on command-line arguments.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */


public class EditorStartingStrategyFactory {

    /**
     * Gets the appropriate `startingStrategy` given command-line arguments `argsEditor`
     * @param argsEditor command-line arguments
     * @return The Appropriate `startingStrategy` to be used
     */
    public StartingStrategy getStartingStrategy(String[] argsEditor) {
        StartingStrategy startingStrategy = null;
        int argNum = argsEditor.length;
        // more than 1 argument in command-line argument
        if (argNum > 1){
            System.out.println("Incorrect usage. Pass in a single file or folder as a command-line argument. Starting in Edit mode");
            startingStrategy = new EditStartingStrategy();
        }
        // Singular file
        else if (argNum == 1){
            if (argsEditor[0].endsWith(".xml")) {
                startingStrategy = new FileStartingStrategy();
            }
            // Folder
            else {
                startingStrategy = new FolderStartingStrategy();
            }
        }
        // Empty/no command-line-arguments
        else {
            startingStrategy = new EditStartingStrategy();
        }
        return startingStrategy;
    }
}
