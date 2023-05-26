package src.game;

/**
 * Abstract Decorator for the `AutoPlayer`, wraps a base `AutoPlayer` to provide additional functionality.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;

public class AutoPlayerDecorator implements AutoPlayer{
    private final AutoPlayer decoratedAutoPlayer;

    public AutoPlayerDecorator(AutoPlayer autoPlayer){
        this.decoratedAutoPlayer = autoPlayer;
    }

    public AutoPlayer getDecoratedAutoPlayer() {
        return decoratedAutoPlayer;
    }

    /**
     * Updates `tilesMap` based on the presence of different tiles within the 4 adjacent Locations to `PacActor`.
     */
    @Override
    public void computeWeights() {
        decoratedAutoPlayer.computeWeights();
    }
}
