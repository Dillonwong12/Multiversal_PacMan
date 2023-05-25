package src.editor.matachi.mapeditor.editor;

import src.game.GameTesterFacade;

public class FolderStartingStrategy implements StartingStrategy {

    @Override
    public void startEditor(String[] argsEditor) throws Exception {
        Controller controller = Controller.getInstance();
        GameTesterFacade gameTesterFacade = new GameTesterFacade();
        gameTesterFacade.testGame(argsEditor);
    }
}


