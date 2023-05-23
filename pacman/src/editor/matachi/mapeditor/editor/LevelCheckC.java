package src.editor.matachi.mapeditor.editor;

import src.editor.matachi.mapeditor.grid.Grid;
import src.editor.matachi.mapeditor.grid.GridModel;

public class LevelCheckC implements LevelCheckStrategy {

    @Override
    public boolean checkLevel(Grid currentModel) {
        int goldAndPillCount = 0;

        for(int y = 0; y < currentModel.getHeight(); y++){
            for(int x = 0; x < currentModel.getWidth(); x++){
                char cellValue = currentModel.getTile(x, y);

                // check if the tile is a gold or pill tile
                if(cellValue == 'g' || cellValue == 'p'){
                    goldAndPillCount++;
                }
            }
        }

        // less than 2 gold and pill tiles
        if(goldAndPillCount < 2){
            System.out.println("[Level – less than 2 Gold and Pill]");
            return false;
        }

        return true;
    }
}
