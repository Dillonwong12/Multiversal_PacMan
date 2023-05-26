package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import src.game.GameTesterFacade;

/**
 * Editor Starting Strategy when a folder is given as an input
 */
public class FolderStartingStrategy implements StartingStrategy {
    /**
     * When a folder is passed as a command line argument we would need to Perform game check
     * and then given it passes game check we would do game testing.
     * Call GameTesterFacade to streamline
     * Regardless of outcome of game testing we would return to empty editor.
     * @param argsEditor Command line arguments
     * @throws Exception
     */
    @Override
    public void startEditor(String[] argsEditor) throws Exception {
        Controller controller = Controller.getInstance();
        GameTesterFacade gameTesterFacade = new GameTesterFacade();
        gameTesterFacade.testGame(argsEditor);
    }
}


