package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import src.editor.matachi.mapeditor.grid.Grid;

/**
 * Singular Level check class which implements LevelCheck interface, checks whether a given map is valid under check C
 * Level Check C at least two Gold and Pill in total
 */
public class LevelCheckC implements LevelCheck {
    /**
     * Checks if there are a total of 2 or more gold and pill combined in the current map
     * @param currentModel the current map/grid
     * @param fileName filename containing the current map
     * @return true if there are more than 2 gold and pill combined and false if there are not more than 2.
     */
    @Override
    public boolean checkLevel(Grid currentModel, String fileName) {
        int goldAndPillCount = 0;

        for(int y = 0; y < currentModel.getHeight(); y++){
            for(int x = 0; x < currentModel.getWidth(); x++){
                char cellValue = currentModel.getTile(x, y);

                // check if the tile is a gold or pill tile
                if(cellValue == 'd' || cellValue == 'c'){
                    goldAndPillCount++;
                }
            }
        }

        // less than 2 gold and pill tiles
        if(goldAndPillCount < 2){
            ErrorLogger.getInstance().writeString(String.format("[Level %s less than 2 Gold and Pill]", fileName));
            return false;
        }

        return true;
    }
}
