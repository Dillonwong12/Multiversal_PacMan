/**
 * Subclass of Actor. Superclass of PacActor and Monster.
 */
package src.game;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public abstract class Character extends Actor{
    private ArrayList<Location> visitedList = new ArrayList<Location>();
    private Game game;
    private int seed = 0;
    private Random randomiser = new Random();
    private final int LIST_LENGTH = 10;

    // Constructor for PacActor
    public Character(boolean b, String sprite, int nbSprites, Game game) {
        super(b, sprite, nbSprites);
        this.game = game;
    }

    // Constructor for Monster
    public Character(String sprite, Game game) {
        super(sprite);
        this.game = game;
    }

    public abstract void act();

    /**
     * Keeps track of the last 10 Locations visited by a Character
     * @param location The locations being added
     */
    protected void addVisitedList(Location location)
    {
        visitedList.add(location);
        if (visitedList.size() == LIST_LENGTH)
            visitedList.remove(0);
    }

    protected boolean isVisited(Location location)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

    /**
     * Determines the validity of a move to a `location`. Valid Locations are those which are not walls, and within the
     * dimensions of the game grid.
     * @param location The location being checked
     * @return A boolean value indicating true if `location` can be moved to, false otherwise
     */
    protected boolean canMove(Location location)
    {
        Color c = getBackground().getColor(location);
        return (!c.equals(Color.gray) && location.getX() < this.getGame().getNumHorzCells()
                && location.getX() >= 0 && location.getY() < this.getGame().getNumVertCells() && location.getY() >= 0);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Sets the seed for a Character's `randomiser`
     * @param seed The seed for the `randomiser`
     */
    public void setSeed(int seed) {
        this.seed = seed;
        randomiser.setSeed(seed);
    }

    public Random getRandomiser() {
        return randomiser;
    }
}
