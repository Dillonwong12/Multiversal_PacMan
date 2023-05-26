/**
 * Subclass of Monster. Attempts to walk towards PacActor if it can. It determines the compass direction from itself
 * to PacActor and moves towards that direction. If the next Location is a wall or has been visited, it will walk
 * randomly.
 */
package src.game;

/**
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;

public class TX5 extends Monster {
    private static final String TX5_TYPE = "TX5";

    public TX5(Game game) {
        super("sprites/" + MonsterType.TX5.getImageName(), game, MonsterType.TX5);
    }

    /***
     * Overrides walkApproach of Monster class. TX5 attempts to walk towards PacActor if possible. If the `next`
     * Location is invalid, walks randomly instead.
     */
    @Override
    public void walkApproach() {
        Location pacLocation = this.getGame().pacActor.getLocation();
        double oldDirection = getDirection();

        // Determine direction to pacActor and try to move in that direction. Otherwise, random walk.
        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        if (!isVisited(next) && canMove(next))
        {
            // If furious, check if it can move two steps in the same direction
            if (isFurious()){
                Location doubleStep = next.getNeighbourLocation(compassDir);
                if (!isVisited(doubleStep) && canMove(doubleStep)){
                    next = doubleStep;
                }
                // Otherwise, walk randomly (still tries to move two steps in the parent walkApproach method)
                else {
                    setDirection(oldDirection);
                    super.walkApproach();
                    return;
                }
            }
            setLocation(next);
            addVisitedList(next);
            this.getGame().getGameCallback().monsterLocationChanged(this);
        }
        else
        {
            // Random walk
            setDirection(oldDirection);
            super.walkApproach();
        }
    }

    public String getType(){
        return TX5_TYPE;
    }
}
