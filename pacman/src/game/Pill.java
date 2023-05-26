/**
 * Subclass of Item.
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

public class Pill extends Item {
    public static final Color PILL_COLOR = Color.white;
    public static final String PILL_TYPE = "pills";
    public static final int PILL_POINTS = 1;

    public Pill () {
        super();
    }

    /**
     * Overrides putItem in Item. Sets the paint color to `PILL_COLOR`, and calls the parent method to draw a filled
     * circle.
     * @param bg The background
     * @param point The location being painted
     */
    public void putItem(GGBackground bg, Point point) {
        bg.setPaintColor(PILL_COLOR);
        super.putItem(bg, point);
    }

    public Color getColor() {
        return PILL_COLOR;
    }

    public String getType(){
        return PILL_TYPE;
    }

    public int getPoints(){
        return PILL_POINTS;
    }
}