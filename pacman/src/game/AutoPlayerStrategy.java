package src.game;

import ch.aplu.jgamegrid.Location;

public interface AutoPlayerStrategy extends AutoPlayer {
    Location moveInAutoMode(PacActor pacActor);
}
