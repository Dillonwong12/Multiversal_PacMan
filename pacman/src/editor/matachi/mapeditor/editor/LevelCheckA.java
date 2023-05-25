package src.editor.matachi.mapeditor.editor;

import src.editor.matachi.mapeditor.grid.Grid;

public class LevelCheckA implements LevelCheck {
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
