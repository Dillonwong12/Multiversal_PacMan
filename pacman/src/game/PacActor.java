/**
 * Subclass of Character. Defines the Character which is controlled by the player and must collect Items to win whilst
 * avoiding Monsters.
 */
// PacActor.java
// Used for PacMan
package src.game;

/**
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.GGKeyRepeatListener;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PacActor extends Character implements GGKeyRepeatListener
{
  private static final String PAC_SPRITE = "sprites/pacpix.gif";
  private static final int nbSprites = 4;
  private int idSprite = 0;
  private int nbPills = 0;
  private int score = 0;
  private ArrayList<Location> pillAndGoldLocations = new ArrayList<>();
  private boolean isAuto = false;
  private AutoPlayerBuilder autoPlayerBuilder = new AutoPlayerBuilder();

  public PacActor(Game game)
  {
    super(true, PAC_SPRITE, nbSprites, game);  // Rotatable
  }

  /**
   * Defines the movement of PacActor according to different `keyCode`s.
   * @param keyCode The code for a keyboard input
   */
  public void keyRepeated(int keyCode)
  {
    if (isAuto) {
      return;
    }
    if (isRemoved())  // Already removed
      return;
    Location next = null;
    switch (keyCode)
    {
      case KeyEvent.VK_LEFT:
        next = getLocation().getNeighbourLocation(Location.WEST);
        setDirection(Location.WEST);
        break;
      case KeyEvent.VK_UP:
        next = getLocation().getNeighbourLocation(Location.NORTH);
        setDirection(Location.NORTH);
        break;
      case KeyEvent.VK_RIGHT:
        next = getLocation().getNeighbourLocation(Location.EAST);
        setDirection(Location.EAST);
        break;
      case KeyEvent.VK_DOWN:
        next = getLocation().getNeighbourLocation(Location.SOUTH);
        setDirection(Location.SOUTH);
        break;
    }
    if (next != null && canMove(next))
    {
      setLocation(next);
      eatItem(next);
    }
  }

  /**
   * Updates `idSprite` and makes PacActor move automatically if `isAuto`
   */
  @Override
  public void act()
  {
    show(idSprite);
    idSprite++;
    if (idSprite == nbSprites)
      idSprite = 0;

    if (isAuto) {
      AutoPlayer autoPlayer = autoPlayerBuilder.build(this);
      Location next = autoPlayerBuilder.getMoveStrategy().moveInAutoMode(this);
      setDirection(getLocation().get4CompassDirectionTo(next));
      setLocation(next);
      eatItem(next);
      addVisitedList(next);
    }
    this.getGame().getGameCallback().pacManLocationChanged(getLocation(), score, nbPills);
  }


  /***
   * PacActor eats an Item at the specified `location`. This removes the Item from the grid, increments `score` &
   * `nbPills`, and applies Item effects if required.
   * @param location The location of the Item being eaten
   */
  private void eatItem(Location location)
  {
    Color c = getBackground().getColor(location);

    if (c.equals(Portal.PORTAL_COLOR)){
      for (Location loc: getGame().getItemLocations().keySet()) {
        if (loc.equals(location)) {
          Item item = getGame().getItemLocations().get(loc);
          Portal p = (Portal)item;
          p.teleport(this);
        }
      }
    }

    // Only Pills and Gold pieces increase `nbPills`
    if (c.equals(Pill.PILL_COLOR) || c.equals(Gold.GOLD_COLOR)){
      nbPills++;
    }

    if (!c.equals(Color.lightGray) && !c.equals(Portal.PORTAL_COLOR)){
      getBackground().fillCell(location, Color.lightGray);
      this.getGame().removeItem(location);
    }

    String title = "[PacMan in the Multiverse] Current score: " + score;
    gameGrid.setTitle(title);
  }

  public int getNbPills() {
    return nbPills;
  }

  public int getScore(){
    return score;
  }

  public void setAuto(boolean auto) {
    isAuto = auto;
  }

  public void setScore(int score){
    this.score = score;
  }

  public void setPillAndGoldLocations(ArrayList<Location> itemLocations){
    pillAndGoldLocations.addAll(itemLocations);
  }

}

