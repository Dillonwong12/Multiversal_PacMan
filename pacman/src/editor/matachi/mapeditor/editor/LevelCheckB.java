package src.editor.matachi.mapeditor.editor;

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.HashMap;
public class LevelCheckB implements LevelCheck {

    @Override
    public boolean checkLevel(Grid currentModel, String fileName) {
        // hashmap of portal colors and their locations
        HashMap<Character, String> PortalLocations = new HashMap<Character, String>();
        // hashmap of portal colors and their count
        HashMap<Character, Integer> PortalCount = new HashMap<Character, Integer>();
        // hashmap of portal mapping and their color names
        HashMap<Character, String> PortalNames = new HashMap<Character, String>();
        PortalNames.put('i', "White");
        PortalNames.put('j', "Yellow");
        PortalNames.put('k', "Dark Gold");
        PortalNames.put('l', "Dark Gray");

        int height = currentModel.getHeight();
        int width = currentModel.getWidth();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                char tileChar = currentModel.getTile(x, y);
                if (tileChar == 'i' || tileChar == 'j'  || tileChar == 'k' || tileChar == 'l') {
                    PortalCount.put(tileChar, PortalCount.getOrDefault(tileChar, 0) + 1);
                    PortalLocations.put(tileChar, PortalLocations.getOrDefault(tileChar, "") + String.format(" (%d, %d);", x+1, y+1));
                }
            }
        }
        for (Character portalColor : PortalCount.keySet()) {
            if (PortalCount.get(portalColor) != 2) {
                String portalLocations = PortalLocations.get(portalColor);
                ErrorLogger.getInstance().writeString("[Level " + fileName + " portal " + PortalNames.get(portalColor) + " count is not 2:" + portalLocations.substring(0, portalLocations.length()-1) + "]");
                return false;
            }
        }
        return true;
    }
}
