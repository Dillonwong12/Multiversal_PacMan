package src.editor.matachi.mapeditor.editor;

import java.util.ArrayList;

public class CompositeABGameCheck extends CompositeGameCheck{
    public CompositeABGameCheck() {
        addGameCheck(new GameCheckA());
        addGameCheck(new GameCheckB());
    }

}
