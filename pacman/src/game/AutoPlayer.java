package src.game;

/**
 * Interface for the `AutoPlayer`, defining weight computation for different `Tiles`
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.HashMap;

public interface AutoPlayer {
    // A HashMap of Characters representing tiles to their weight values
    HashMap<java.lang.Character, Integer> tilesMap = new HashMap<>();

    /**
     * Updates `tilesMap` based on the presence of different tiles within the 4 adjacent Locations to `PacActor`.
     */
    void computeWeights();

    default HashMap<java.lang.Character, Integer> getTilesMap(){
        return this.tilesMap;
    }

    /**
     * Gets all the 4 neighbouring `Location`s iteratively.
     * @return An ArrayList of all the valid neighbours
     */
    default ArrayList<Location> getNeighbourhood(PacActor pacActor) {
        int x = pacActor.getX();
        int y = pacActor.getY();
        ArrayList<Location> neighbourhood = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Location location = new Location(x + i, y + j);
                if (!pacActor.canMove(location) || Math.abs(i) == Math.abs(j)){
                    continue;
                }

                neighbourhood.add(location);
            }
        }
        return neighbourhood;
    }

    /**
     * Finds and returns the closest `Pill` or `Gold` `Location`.
     * @return currentLocation The `Location` closest to a `Pill` or `Gold`
     */
    default Location closestPillLocation(ArrayList<Location> pillAndGoldLocations, Location init) {
        int currentDistance = 1000;
        Location currentLocation = null;
        for (Location location: pillAndGoldLocations) {
            int distanceToPill = location.getDistanceTo(init);
            if (distanceToPill < currentDistance) {
                currentLocation = location;
                currentDistance = distanceToPill;
            }
        }
        return currentLocation;
    }
}
