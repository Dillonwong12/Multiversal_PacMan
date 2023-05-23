package src.editor.matachi.mapeditor.editor;

import org.jdom.JDOMException;
import org.jdom.Element;
import src.editor.matachi.mapeditor.grid.GridModel;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.util.HashMap;
public class LevelCheckB implements LevelCheckStrategy {

    @Override
    public boolean checkLevel(GridModel currentModel) {
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
                System.out.println("[Level – portal " + PortalNames.get(portalColor) + " count is not 2:" + PortalLocations.get(portalColor) + "]");
                return false;
            }
        }
        return true;
    }
}
