package src.editor.matachi.mapeditor.editor;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import src.editor.matachi.mapeditor.grid.Grid;
import src.editor.matachi.mapeditor.grid.GridModel;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LevelCheckA implements LevelCheckStrategy{
    @Override
    public boolean checkLevel(Grid currentModel, String fileName) {
        int pacmanCount = 0;
        String pacmanLocations = "";

        int height = currentModel.getHeight();
        int width = currentModel.getWidth();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                char tileChar = currentModel.getTile(x, y);
                if (tileChar == 'f') {
                    pacmanCount++;
                    pacmanLocations += String.format(" (%d, %d);", x+1, y+1);
                }
            }
        }
        if (pacmanCount == 1) {
            return true;
        } else if (pacmanCount > 1){
            pacmanLocations = pacmanLocations.substring(0, pacmanLocations.length()-1);
            ErrorLogger.getInstance().writeString(String.format("[Level %s – more than one start for Pacman: %s]", fileName, pacmanLocations));
            return false;
        }
        ErrorLogger.getInstance().writeString(String.format("[Level %s – no start for PacMan]", fileName));
        return false;
    }
}
