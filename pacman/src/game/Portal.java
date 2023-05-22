/**
 * Subclass of Item. Teleports PacActor and Monsters to `pairPortal`'s Location.
 */

package src.game;

import ch.aplu.jgamegrid.GGBackground;

import java.awt.*;
import java.util.ArrayList;

public class Portal extends Item {
    public static final Color PORTAL_COLOR = Color.black;
    public static final String PORTAL_TYPE = "portal";
    public static final int PORTAL_POINTS = 0;
    private Portal pairPortal;

    public Portal (String sprite){
        super(sprite);
    }

    /**
     * Overrides putItem in Item. Sets the paint color to `PORTAL_COLOR`, and calls the parent method to draw a filled
     * circle.
     * @param bg The background
     * @param point The location being painted
     */
    public void putItem(GGBackground bg, Point point){
        bg.setPaintColor(PORTAL_COLOR);
        super.putItem(bg, point);
    }

    public void teleport(Character character){
        character.setLocation(pairPortal.getLocation());
    }

    public Color getColor() {
        return PORTAL_COLOR;
    }

    public String getType(){
        return PORTAL_TYPE;
    }

    public int getPoints(){
        return PORTAL_POINTS;
    }

    public void setPairPortal(Portal portal){
        this.pairPortal = portal;
    }
}
