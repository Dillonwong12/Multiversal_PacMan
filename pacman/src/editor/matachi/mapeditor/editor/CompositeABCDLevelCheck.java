package src.editor.matachi.mapeditor.editor;

import java.io.File;
import java.util.Properties;

public class CompositeABCDLevelCheck extends CompositeLevelCheck {

    public CompositeABCDLevelCheck() {
        addLevelCheck(new LevelCheckA());
        addLevelCheck(new LevelCheckB());
        addLevelCheck(new LevelCheckC());
        addLevelCheck(new LevelCheckD());
    }
}
