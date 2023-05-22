/**
 * Subclass of Character. Defines the Character which is controlled by the player and must collect Items to win whilst
 * avoiding Monsters.
 */
// PacActor.java
// Used for PacMan
package src.game;

import ch.aplu.jgamegrid.GGKeyRepeatListener;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PacActor extends Character implements GGKeyRepeatListener
{
  private static final String PAC_SPRITE = "sprites/pacpix.gif";
  private static final int nbSprites = 4;
  private int idSprite = 0;
  private int nbPills = 0;
  private int score = 0;

  private List<String> propertyMoves = new ArrayList<>();
  private int propertyMoveIndex = 0;
  private boolean isAuto = false;

  public PacActor(Game game)
  {
    super(true, PAC_SPRITE, nbSprites, game);  // Rotatable
  }

  /**
   * Parses a `propertyMoveString` to store a list of moves in `propertyMoves`. Used for Auto Testing.
   */
  public void setPropertyMoves(String propertyMoveString) {
    if (propertyMoveString != null) {
      this.propertyMoves = Arrays.asList(propertyMoveString.split(","));
    }
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
      moveInAutoMode();
    }
    this.getGame().getGameCallback().pacManLocationChanged(getLocation(), score, nbPills);
  }

  /**
   * Finds and returns the Location closest
   * @return currentLocation The Location closest to an Item
   */
  private Location closestPillLocation() {
    int currentDistance = 1000;
    Location currentLocation = null;
    List<Location> pillAndItemLocations = this.getGame().getPillAndItemLocations();
    for (Location location: pillAndItemLocations) {
      int distanceToPill = location.getDistanceTo(getLocation());
      if (distanceToPill < currentDistance) {
        currentLocation = location;
        currentDistance = distanceToPill;
      }
    }
    return currentLocation;
  }

  /**
   * Moves PacActor according to the moves specified in `propertyMoves`
   */
  private void followPropertyMoves() {
    String currentMove = propertyMoves.get(propertyMoveIndex);
    switch(currentMove) {
      case "R":
        turn(90);
        break;
      case "L":
        turn(-90);
        break;
      case "M":
        Location next = getNextMoveLocation();
        if (canMove(next)) {
          setLocation(next);
          eatItem(next);
        }
        break;
    }
    propertyMoveIndex++;
  }

  /**
   * First moves PacActor according to the moves specified in `propertyMoves`. Once there are no more moves to follow
   * from `propertyMoves`, attempts to walk towards the next `closestPill`.
   */
  private void moveInAutoMode() {
    if (propertyMoves.size() > propertyMoveIndex) {
      followPropertyMoves();
      return;
    }
    Location closestPill = closestPillLocation();
    double oldDirection = getDirection();

    Location.CompassDirection compassDir =
            getLocation().get4CompassDirectionTo(closestPill);
    Location next = getLocation().getNeighbourLocation(compassDir);
    setDirection(compassDir);
    if (!isVisited(next) && canMove(next)) {
      setLocation(next);
    } else {
      // normal movement
      int sign = this.getRandomiser().nextDouble() < 0.5 ? 1 : -1;
      setDirection(oldDirection);
      turn(sign * 90);  // Try to turn left/right
      next = getNextMoveLocation();
      if (canMove(next)) {
        setLocation(next);
      } else {
        setDirection(oldDirection);
        next = getNextMoveLocation();
        if (canMove(next)) // Try to move forward
        {
          setLocation(next);
        } else {
          setDirection(oldDirection);
          turn(-sign * 90);  // Try to turn right/left
          next = getNextMoveLocation();
          if (canMove(next)) {
            setLocation(next);
          } else {
            setDirection(oldDirection);
            turn(180);  // Turn backward
            next = getNextMoveLocation();
            setLocation(next);
          }
        }
      }
    }
    eatItem(next);
    addVisitedList(next);
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
}
