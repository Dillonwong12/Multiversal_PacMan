package src.game;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.HashMap;

public interface AutoPlayer {
    HashMap<java.lang.Character, Integer> tilesMap = new HashMap<>();

    void computeWeights();

    Location moveInAutoMode(PacActor pacActor);

    default HashMap<java.lang.Character, Integer> getTilesMap(){
        return this.tilesMap;
    }

    /**
     * gets all the 4 neighbours iteratively
     * @return an ArrayList of all the valid neighbours
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

                // Exclude The Current Location
                neighbourhood.add(location);
            }
        }
        return neighbourhood;
    }

    /**
     * Finds and returns the Location closest
     * @return currentLocation The Location closest to an Item
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
