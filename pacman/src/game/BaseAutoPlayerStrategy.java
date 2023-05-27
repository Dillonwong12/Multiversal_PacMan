package src.game;

/**
 * Concrete `AutoPlayerStrategy`. Moves `PacActor` towards the closest `Pill` or `Gold`, ignoring `Monsters`.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;
import src.editor.matachi.mapeditor.grid.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseAutoPlayerStrategy implements AutoPlayerStrategy{

    // Weights for `Pill` and `Gold`. These may be adjusted in the future
    private final int PILL_WEIGHT = 1;
    private final int GOLD_WEIGHT = 1;

    private final int STEPS_AFTER_TELEPORT = 15;

    private AutoPlayerBuilder builder;
    private Location targetLocation = null;

    public BaseAutoPlayerStrategy(AutoPlayerBuilder builder, Location targetLocation){
        this.builder = builder;
        this.targetLocation = targetLocation;
    }

    /**
     * Moves `PacActor` automatically towards the closest `Pill` or `Gold`, ignoring `Monsters`.
     * @param pacActor PacMan
     * @return
     */
    public Location moveInAutoMode(PacActor pacActor) {
        ArrayList<Location> neighbourhood = getNeighbourhood(pacActor, pacActor.getLocation());
        // Set the `targetLocation` if it hasn't already been set

        targetLocation = firstPillDFS(pacActor);
        builder.setTargetLocation(targetLocation);

        // Rearrange the moves
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
        Color nextColor = pacActor.getBackground().getColor(next);
        // Update the number of steps `pacActor` has taken after teleporting
        if (nextColor.equals(Portal.PORTAL_COLOR) || pacActor.getStepsAfterTeleport() > 0){
            pacActor.setStepsAfterTeleport(pacActor.getStepsAfterTeleport()+1);
        }

        return next;
    }


    /**
     * Gets the next best move based on shortest distance to the `targetLocation`
     * @param targetLocation the target that we want to move to
     * @return an ArrayList of the best moves based on distance to the target
     */
    private ArrayList<Location> getNextMoves(PacActor pacActor, Location targetLocation) {
        ArrayList<Location> candidates = new ArrayList<>();
        double shortestDistance = Double.MAX_VALUE;

        // Check Each Neighbor Location
        for (Location neighbor : getNeighbourhood(pacActor, pacActor.getLocation())) {

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

    /**
     * Updates `tilesMap` based on the presence of `Pills`s and `Gold`, incrementing their weights in `tilesMap` by
     * `PILL_WEIGHT` or `GOLD_WEIGHT` respectively.
     */
    @Override
    public void computeWeights() {
        HashMap<java.lang.Character, Integer> tilesMap = getTilesMap();
        tilesMap.put('c', tilesMap.getOrDefault('c', 0)+PILL_WEIGHT);
        tilesMap.put('d', tilesMap.getOrDefault('d', 0)+GOLD_WEIGHT);
    }

    /**
     * Depth-First Search to return the first found `Pill` or `Gold`  pr 'Portal' Location
     * @param pacActor PacMan
     * @return Location of the first found `Pill` or `Gold`
     */
    private Location firstPillDFS(PacActor pacActor) {
        ArrayList<Location> visited = new ArrayList<>();
        ArrayList<Location> stack = new ArrayList<>();
        Location curr = pacActor.getLocation();

        // Reset the number of steps `pacActor` has taken after teleporting
        if (pacActor.getStepsAfterTeleport() > STEPS_AFTER_TELEPORT){
            pacActor.setStepsAfterTeleport(0);
        }

        stack.add(curr);
        // Keep popping nodes off the stack until there are no more nodes to explore
        while (stack.size() > 0){
            curr = stack.remove(stack.size()-1);
            visited.add(curr);

            // Collect the 4 neighbouring `Points`
            ArrayList<Location> neighbours = getNeighbourhood(pacActor, curr);

            for (Location neighbour : neighbours){
                // If the `neighbour` hasn't been visited yet and is within the bounds
                if (!visited.contains(neighbour)){

                    Color color = pacActor.getBackground().getColor(neighbour);
                    if (color.equals(Pill.PILL_COLOR) || color.equals(Gold.GOLD_COLOR)
                            || (color.equals(Portal.PORTAL_COLOR) && pacActor.getStepsAfterTeleport() == 0)){
                        return neighbour;
                    }
                    stack.add(neighbour);

                }
            }
        }
        return curr;
    }
}
