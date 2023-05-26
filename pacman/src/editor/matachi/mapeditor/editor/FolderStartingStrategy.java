package src.editor.matachi.mapeditor.editor;

/**
 * `StartingStrategy` which opens the Editor to Game Test Mode.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import src.game.GameTesterFacade;

/**
 * Editor `StartingStrategy` when a folder is given as a command-line argument
 */
public class FolderStartingStrategy implements StartingStrategy {
    /**
     * Attempt to perform a game test via `GameTesterFacade` if a folder is in passed as a command-line argument
     * @param argsEditor Command-line arguments
     * @throws Exception
     */
    @Override
    public void startEditor(String[] argsEditor) throws Exception {
        Controller controller = Controller.getInstance();
        GameTesterFacade gameTesterFacade = new GameTesterFacade();
        gameTesterFacade.testGame(argsEditor);
    }
}


