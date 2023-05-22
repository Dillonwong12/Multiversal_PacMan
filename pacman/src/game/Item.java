/**
 * Subclass of Actor. Superclass of Gold, Ice, and Pill.
 */
package src.game;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;

import java.awt.*;

public abstract class Item extends Actor{
    private static final int CIRCLE_RADIUS = 5;

    public Item(String sprite){
        super(sprite);
    }

    public Item(){
        super();
    }

    /**
     * Draws a filled circle of `CIRCLE_RADIUS` at the `point` in `bg`, but is first overridden in children methods
     * to set the paint color.
     * @param bg The background
     * @param point The location being painted
     */
    public void putItem(GGBackground bg, Point point){
        bg.fillCircle(point, CIRCLE_RADIUS);
    }

    public abstract Color getColor();

    public abstract String getType();

    public abstract int getPoints();
}
