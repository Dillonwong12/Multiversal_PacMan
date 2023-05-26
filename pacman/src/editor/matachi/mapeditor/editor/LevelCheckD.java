package src.editor.matachi.mapeditor.editor;

/**
 * Leaf class for checking levels with the Composite Pattern. Checks that each Gold and Pill is accessible to `PacActor`
 * from the starting point, ignoring `Monsters` but accounting for valid `Portals`.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import src.editor.matachi.mapeditor.grid.Grid;

import java.util.ArrayList;
import java.awt.Point;

public class LevelCheckD implements LevelCheck {
    ArrayList<Point> pillAndGoldLocations = new ArrayList<>();
    ArrayList<Point> stack = new ArrayList<>();

    /**
     * Checks that each Gold and Pill is accessible to `PacActor`
     * @param currentModel Grid representation of the map
     * @param fileName File name
     * @return true if each Gold and Pill is accessible to `PacActor`, false otherwise
     */
    @Override
    public boolean checkLevel(Grid currentModel, String fileName) {
        int pacManX = 0;
        int pacManY = 0;

        for(int y = 0; y < currentModel.getHeight(); y++){
            for(int x = 0; x < currentModel.getWidth(); x++){
                char cellValue = currentModel.getTile(x, y);

                // Check if the tile is a gold or pill tile
                if(cellValue == 'd' || cellValue == 'c'){
                    pillAndGoldLocations.add(new Point(x, y));
                }
                else if (cellValue == 'f'){
                    pacManX = x;
                    pacManY = y;
                }
            }
        }
        // Uses DFS to constantly keep track of the number of accessible `Gold` and `Pills`. If this is ever
        // equal to the number of `pillAndGoldLocations`, the check has passed.
        if (DFS(currentModel, new Point(pacManX, pacManY))){
            return true;
        }
        // Format ErrorLogger strings
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

    /**
     * Depth-First Search to see if each Gold and Pill is accessible to `PacActor`.
     * @param currentModel Grid representation of the map
     * @param root The starting point
     * @return true if each Gold and Pill is accessible to `PacActor`, false otherwise
     */
    private boolean DFS(Grid currentModel, Point root) {
        ArrayList<Point> visited = new ArrayList<>();
        Point curr = root;
        stack.add(root);
        // Keep popping nodes off the stack until there are no more nodes to explore
        while (stack.size() > 0){
            curr = stack.remove(stack.size()-1);
            visited.add(curr);

            int X = (int)curr.getX();
            int Y = (int)curr.getY();
            char currTile = currentModel.getTile(X, Y);
            // Collect the 4 neighbouring `Points`
            ArrayList<Point> neighbours = new ArrayList<>();
            neighbours.add(new Point(X+1, Y));
            neighbours.add(new Point(X, Y+1));
            neighbours.add(new Point(X-1, Y));
            neighbours.add(new Point(X, Y-1));
            for (Point neighbour : neighbours){
                // If the `neighbour` hasn't been visited yet and is within the bounds
                if (!visited.contains(neighbour) && neighbour.getX() >= 0 && neighbour.getX() < 20 && neighbour.getY() >= 0 && neighbour.getY() < 11){
                    char tile = currentModel.getTile((int)neighbour.getX(), (int)neighbour.getY());
                    if (tile == 'b'){
                        continue;
                    }
                    // Handle `Portals`
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

            // Keep track of the number of `Gold` and `Pills` found. If there are none left, the check has passed
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
