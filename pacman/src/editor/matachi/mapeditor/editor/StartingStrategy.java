package src.editor.matachi.mapeditor.editor;

import org.jdom.JDOMException;

import java.io.IOException;

public interface StartingStrategy {
    void startEditor(String[] argsEditor) throws Exception;
}
