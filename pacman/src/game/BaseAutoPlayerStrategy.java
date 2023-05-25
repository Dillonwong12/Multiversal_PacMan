package src.game;

import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseAutoPlayerStrategy implements AutoPlayerStrategy{
    private final int PILL_WEIGHT = 1;
    private final int GOLD_WEIGHT = 1;

    private AutoPlayerBuilder builder;
    private Location targetLocation = null;

    public BaseAutoPlayerStrategy(AutoPlayerBuilder builder, Location targetLocation){
        this.builder = builder;
        this.targetLocation = targetLocation;
    }

    public Location moveInAutoMode(PacActor pacActor) {
        ArrayList<Location> neighbourhood = getNeighbourhood(pacActor);
        if (targetLocation != null){
            if (pacActor.getLocation().equals(targetLocation)){
                builder.removeTargetLocation();
            }
        }
        else {
            targetLocation = closestPillLocation(pacActor.getGame().getPillAndItemLocations(), pacActor.getLocation());
            System.out.println(targetLocation);
            builder.setTargetLocation(targetLocation);
        }
        ArrayList<Location> candidates = getNextMoves(pacActor, targetLocation);
        neighbourhood.removeAll(candidates);
        candidates.addAll(neighbourhood);


        Location next = null;

        // Select a Location randomly if there is more than one Location sharing the same shortest distance to the Gold
        if (!candidates.isEmpty()) {
            next = candidates.get(0);
            for (int i = 1; i < candidates.size(); i++){
                // if the next location is not visited, then we take it as the next location
                if (!pacActor.isVisited(next)){
                    break;
                }
                next = candidates.get(i);
            }
        }

        return next;
    }


    /**
     * Gets the next best move based on shortest distance to the target location
     * @param targetLocation the target that we want to move to
     * @return an ArrayList of the best moves based on distance to the target
     */
    public ArrayList<Location> getNextMoves(PacActor pacActor, Location targetLocation) {
        ArrayList<Location> candidates = new ArrayList<>();
        double shortestDistance = Double.MAX_VALUE;

        // Check Each Neighbor Location
        for (Location neighbor : getNeighbourhood(pacActor)) {

            double distance = neighbor.getDistanceTo(targetLocation);

            if (distance < shortestDistance) {
                candidates.clear();
                candidates.add(neighbor);
                shortestDistance = distance;

            } else if (distance == shortestDistance) {
                candidates.add(neighbor);
            }
        }

        return candidates;
    }

    @Override
    public void computeWeights() {
        HashMap<java.lang.Character, Integer> tilesMap = getTilesMap();
        tilesMap.put('c', tilesMap.getOrDefault('c', 0)+PILL_WEIGHT);
        tilesMap.put('d', tilesMap.getOrDefault('d', 0)+GOLD_WEIGHT);
    }
}
