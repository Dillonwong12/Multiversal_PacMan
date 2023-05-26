package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.ArrayList;
import java.awt.Point;

/**
 * Singular level check class which implements the LevelCheck Interface, checks whether a given map passes Level Check D
 * Level Check D: Check that items are accessible relative to pacman starting location.
 */
public class LevelCheckD implements LevelCheck {
    ArrayList<Point> pillAndGoldLocations = new ArrayList<>();
    ArrayList<Point> stack = new ArrayList<>();

    /**
     * Checks that the all items are access
     * @param currentModel
     * @param fileName
     * @return
     */
    @Override
    public boolean checkLevel(Grid currentModel, String fileName) {
        int pacManX = 0;
        int pacManY = 0;

        for(int y = 0; y < currentModel.getHeight(); y++){
            for(int x = 0; x < currentModel.getWidth(); x++){
                char cellValue = currentModel.getTile(x, y);

                // check if the tile is a gold or pill tile
                if(cellValue == 'd' || cellValue == 'c'){
                    pillAndGoldLocations.add(new Point(x, y));
                }
                else if (cellValue == 'f'){
                    pacManX = x;
                    pacManY = y;
                }
            }
        }

        if (DFS(currentModel, new Point(pacManX, pacManY))){
            return true;
        }
        String inaccessiblePills = "";
        String inaccessibleGold = "";
        for (Point p : pillAndGoldLocations){
            if (currentModel.getTile((int)p.getX(), (int)p.getY()) == 'c'){
                inaccessiblePills += String.format(" (%d, %d);", (int)p.getX()+1, (int)p.getY()+1);
            }
            if (currentModel.getTile((int)p.getX(), (int)p.getY()) == 'd'){
                inaccessibleGold += String.format(" (%d, %d);", (int)p.getX()+1, (int)p.getY()+1);
            }

        }
        if (inaccessiblePills.length() > 0){
            inaccessiblePills = inaccessiblePills.substring(0, inaccessiblePills.length()-1);
            ErrorLogger.getInstance().writeString(String.format("[Level %s – Pill not accessible: %s]", fileName, inaccessiblePills));
        }
        if (inaccessibleGold.length() > 0){
            inaccessibleGold = inaccessibleGold.substring(0, inaccessibleGold.length()-1);
            ErrorLogger.getInstance().writeString(String.format("[Level %s – Gold not accessible: %s]", fileName, inaccessibleGold));
        }
        pillAndGoldLocations.clear();
        return false;
    }

    private boolean DFS(Grid currentModel, Point root) {
        ArrayList<Point> visited = new ArrayList<>();
        Point curr = root;
        stack.add(root);
        while (stack.size() > 0){
            curr = stack.remove(stack.size()-1);
            visited.add(curr);

            int X = (int)curr.getX();
            int Y = (int)curr.getY();
            char currTile = currentModel.getTile(X, Y);
            ArrayList<Point> neighbours = new ArrayList<>();
            neighbours.add(new Point(X+1, Y));
            neighbours.add(new Point(X, Y+1));
            neighbours.add(new Point(X-1, Y));
            neighbours.add(new Point(X, Y-1));
            for (Point neighbour : neighbours){

                if (!visited.contains(neighbour) && neighbour.getX() >= 0 && neighbour.getX() < 20 && neighbour.getY() >= 0 && neighbour.getY() < 11){
                    char tile = currentModel.getTile((int)neighbour.getX(), (int)neighbour.getY());
                    if (tile == 'b'){
                        continue;
                    }
                    else if (tile == 'i' || tile == 'j' || tile == 'k' || tile == 'l'){
                        for(int y = 0; y < currentModel.getHeight(); y++){
                            for(int x = 0; x < currentModel.getWidth(); x++){
                                char cellValue = currentModel.getTile(x, y);

                                // Find pair portal
                                if (cellValue == tile && (x != (int)curr.getX() && y != (int)curr.getY())){
                                    stack.add(new Point(x, y));
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        stack.add(neighbour);
                    }
                }
            }

            if (currTile == 'd' || currTile == 'c'){
                pillAndGoldLocations.remove(curr);
                if (pillAndGoldLocations.size() == 0){
                    return true;
                }
            }
        }
        return false;
    }
}
