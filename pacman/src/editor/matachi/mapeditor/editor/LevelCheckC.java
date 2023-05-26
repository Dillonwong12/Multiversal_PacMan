package src.editor.matachi.mapeditor.editor;

/**
 * Leaf class for checking levels with the Composite Pattern. Checks that there are at least two Gold and Pill in total.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import src.editor.matachi.mapeditor.grid.Grid;

public class LevelCheckC implements LevelCheck {

    /**
     * Checks that there are at least two Gold and Pill in total in the `currentModel`
     * @param currentModel Grid representation of the map
     * @param fileName File name
     * @return true if there are at least two Gold and Pill in total in the `currentModel`, false otherwise
     */
    @Override
    public boolean checkLevel(Grid currentModel, String fileName) {
        int goldAndPillCount = 0;

        for(int y = 0; y < currentModel.getHeight(); y++){
            for(int x = 0; x < currentModel.getWidth(); x++){
                char cellValue = currentModel.getTile(x, y);

                // Check if the tile is a Gold or Pill tile
                if(cellValue == 'd' || cellValue == 'c'){
                    goldAndPillCount++;
                }
            }
        }

        // If there are less than 2 Gold and Pill tiles, the check has failed
        if(goldAndPillCount < 2){
            ErrorLogger.getInstance().writeString(String.format("[Level %s less than 2 Gold and Pill]", fileName));
            return false;
        }

        return true;
    }
}
