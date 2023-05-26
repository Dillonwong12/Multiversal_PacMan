package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import src.editor.matachi.mapeditor.grid.Grid;

/**
 * Singular level check Class which implements LevelCheck interface and checks whether a given map/grid passes level check A
 * Level CheckA: exactly one starting point for PacMan
 */
public class LevelCheckA implements LevelCheck {
    /**
     * Checks if there are only 2 starting point for pacman
     * @param currentModel current map
     * @param fileName filename which contains the current map
     * @return true if it passes level check A or false if it fails
     */
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
