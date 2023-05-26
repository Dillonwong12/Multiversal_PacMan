/**
 * Subclass of Item. Increases PacActor's `score` and causes all Monsters to move faster when eaten.
 */

package src.game;

/**
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.GGBackground;

import java.awt.*;
import java.util.ArrayList;

public class Gold extends Item {
    public static final Color GOLD_COLOR = Color.yellow;
    private static final String GOLD_SPRITE = "sprites/gold.png";
    public static final String GOLD_TYPE = "gold";
    public static final int GOLD_POINTS = 5;
    public static final int FURY_DURATION = 3;

    public Gold (){
        super(GOLD_SPRITE);
    }

    /**
     * Overrides putItem in Item. Sets the paint color to `GOLD_COLOR`, and calls the parent method to draw a filled
     * circle.
     * @param bg The background
     * @param point The location being painted
     */
    public void putItem(GGBackground bg, Point point){
        bg.setPaintColor(GOLD_COLOR);
        super.putItem(bg, point);
    }

    public Color getColor() {
        return GOLD_COLOR;
    }

    public String getSprite() {
        return GOLD_SPRITE;
    }

    public String getType(){
        return GOLD_TYPE;
    }

    public int getPoints(){
        return GOLD_POINTS;
    }
}
