package src.game;


import java.awt.*;
import java.util.ArrayList;
import ch.aplu.jgamegrid.Location;

public class AutoPlayerBuilder {
    private AutoPlayerStrategy strategy;
    private Location targetLocation = null;

    public AutoPlayerStrategy getMoveStrategy(){
        return strategy;
    }

    public void setStrategy(AutoPlayerStrategy strategy) {
        this.strategy = strategy;
    }

    public AutoPlayer build(PacActor pacActor) {
        // Set the strategy here
        AutoPlayer autoPlayer = new BaseAutoPlayerStrategy(this, targetLocation);
        setStrategy((AutoPlayerStrategy) autoPlayer);
        ArrayList<Location> neighbours = autoPlayer.getNeighbourhood(pacActor);

        for (Location location : neighbours) {
            if (pacActor.canMove(location)){
                Color c = pacActor.getBackground().getColor(location);
                if (c.equals(Portal.PORTAL_COLOR)){
                    autoPlayer = new AutoPlayerPortalDecorator(autoPlayer);
                }
                // Future Items and Monsters checks
            }
        }

        autoPlayer.computeWeights();
        return autoPlayer;
    }

    public void setTargetLocation(Location targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void removeTargetLocation() {
        this.targetLocation = null;
    }
}
