/**
 * Subclass of Item. Causes all Monsters to freeze when eaten.
 */

package src.game;

import ch.aplu.jgamegrid.GGBackground;

import java.awt.*;
import java.util.ArrayList;

public class Ice extends Item {

    public static final Color ICE_COLOR = Color.blue;
    private static final String ICE_SPRITE = "sprites/ice.png";
    public static final String ICE_TYPE = "ice";
    public static final int ICE_POINTS = 0;
    public static final int FREEZE_DURATION = 3;

    public Ice() {
        super(ICE_SPRITE);
    }

    /**
     * Overrides putItem in Item. Sets the paint color to `ICE_COLOR`, and calls the parent method to draw a filled
     * circle.
     * @param bg The background
     * @param point The location being painted
     */
    public void putItem(GGBackground bg, Point point) {
        bg.setPaintColor(ICE_COLOR);
        super.putItem(bg, point);
    }

    public Color getColor() {
        return ICE_COLOR;
    }

    public String getSprite() {
        return ICE_SPRITE;
    }

    public String getType(){
        return ICE_TYPE;
    }

    public int getPoints(){
        return ICE_POINTS;
    }
}