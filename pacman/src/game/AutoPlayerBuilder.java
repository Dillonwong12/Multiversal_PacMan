package src.game;

/**
 * Builder for the `AutoPlayer`, builds an `AutoPlayer` using the Strategy and Decorator Patterns.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import java.awt.*;
import java.util.ArrayList;
import ch.aplu.jgamegrid.Location;

public class AutoPlayerBuilder {
    // The selected `AutoPlayerStrategy`
    private AutoPlayerStrategy strategy;

    // The Location `PacActor` is currently targeting
    private Location targetLocation = null;

    public AutoPlayer build(PacActor pacActor) {
        // Set the strategy here. This will depend on different contexts in the future.
        AutoPlayer autoPlayer = new BaseAutoPlayerStrategy(this, targetLocation);
        setStrategy((AutoPlayerStrategy) autoPlayer);
        ArrayList<Location> neighbours = autoPlayer.getNeighbourhood(pacActor);

        // Wrap the base `AutoPlayer`
        for (Location location : neighbours) {
            if (pacActor.canMove(location)){
                Color c = pacActor.getBackground().getColor(location);
                if (c.equals(Portal.PORTAL_COLOR)){
                    autoPlayer = new AutoPlayerPortalDecorator(autoPlayer);
                }
                // Future Items and Monsters checks go here
            }
        }

        // Update the weights depending on the presence of each tile and their individual weights
        autoPlayer.computeWeights();
        return autoPlayer;
    }

    public AutoPlayerStrategy getMoveStrategy(){
        return strategy;
    }

    public void setStrategy(AutoPlayerStrategy strategy) {
        this.strategy = strategy;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void removeTargetLocation() {
        this.targetLocation = null;
    }
}
