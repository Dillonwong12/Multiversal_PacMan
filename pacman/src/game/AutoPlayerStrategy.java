package src.game;

/**
 * `AutoPlayerStrategy` interface for the `AutoPlayer`, defines the strategy to use with the Decorators to move
 * `PacActor` automatically.
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;

public interface AutoPlayerStrategy extends AutoPlayer {

    /**
     * Defines the strategy to use with the Decorators to move `PacActor` automatically.
     * @param pacActor PacMan
     * @return the chosen Location according to the `AutoPlayerStrategy`
     */
    Location moveInAutoMode(PacActor pacActor);
}
