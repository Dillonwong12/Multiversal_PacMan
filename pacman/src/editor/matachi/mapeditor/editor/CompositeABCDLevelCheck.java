package src.editor.matachi.mapeditor.editor;

public class CompositeABCDLevelCheck extends CompositeLevelCheck {

    public CompositeABCDLevelCheck() {
        addLevelCheck(new LevelCheckA());
        addLevelCheck(new LevelCheckB());
        addLevelCheck(new LevelCheckC());
        addLevelCheck(new LevelCheckD());
    }
}
