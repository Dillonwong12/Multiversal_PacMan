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

  private ArrayList<Location> pillAndGoldLocations = new ArrayList<>();
  private Location targetLocation = null;

  private List<String> propertyMoves = new ArrayList<>();
  private int propertyMoveIndex = 0;
  private boolean isAuto = false;

  private int prevAngle = 90;
  private int sign = 1;
  private int turns = 0;

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
    for (Location location: pillAndGoldLocations) {
      int distanceToPill = location.getDistanceTo(getLocation());
      if (distanceToPill < currentDistance) {
        currentLocation = location;
        currentDistance = distanceToPill;
      }
    }
    return currentLocation;
  }

  /**
   * First moves PacActor according to the moves specified in `propertyMoves`. Once there are no more moves to follow
   * from `propertyMoves`, attempts to walk towards the next `closestPill`.
   */
  private void moveInAutoMode() {
    ArrayList<Location> neighbourhood = getNeighbourhood();
    ArrayList<Location> candidates = new ArrayList<>();
    double oldDirection = getDirection();

    for (Location loc : neighbourhood){
      Color c = getBackground().getColor(loc);
      if (canMove(loc) && (c.equals(Gold.GOLD_COLOR) || c.equals(Pill.PILL_COLOR))){
        candidates.add(loc);
      }
    }
    for (Location loc : neighbourhood){
      Color c = getBackground().getColor(loc);
      if (canMove(loc) && (c.equals(Ice.ICE_COLOR))){
        candidates.add(loc);
      }
    }
    if (candidates.size() > 0){
      Location loc = candidates.get(0);
      setDirection(getLocation().get4CompassDirectionTo(loc));
      setLocation(loc);
      eatItem(loc);
      addVisitedList(loc);
      return;
    }


    int angles[] = {sign*90, 0, -sign*90, 180};
    Location next = getNextMoveLocation();
    for (int angle : angles){
      setDirection(oldDirection);
      turn(angle);
      next = getNextMoveLocation();
      if (canMove(next)){
        if (Math.abs(angle) == 90){
          if (angle == prevAngle){
            turns++;
          }
          prevAngle = angle;
        }
        if (turns >= 4){
          sign *= -1;
          turns = 0;
        }
        break;
      }
    }

    setLocation(next);
    eatItem(next);
    addVisitedList(next);

//    Location currentLocation = getLocation();
//
//    if (targetLocation!= null && currentLocation.equals(targetLocation)){
//      pillAndGoldLocations.remove(targetLocation);
//    }
//
//    targetLocation = closestPillLocation();
//    ArrayList<Location> neighbourhood = getNeighbourhood();
//    ArrayList<Location> candidates = getNextMoves(targetLocation);
//    neighbourhood.removeAll(candidates);
//    candidates.addAll(neighbourhood);
//
//    Location next = null;
//
//    // Select a Location randomly if there is more than one Location sharing the same shortest distance to the Gold
//    if (!candidates.isEmpty()) {
//      next = candidates.get(0);
//      for (int i = 1; i < candidates.size(); i++){
//        // if the next location is not visited, then we take it as the next location
//        if (!isVisited(next)){
//          break;
//        }
//        next = candidates.get(i);
//      }
//    }
//    setDirection(getLocation().get4CompassDirectionTo(next));
//    setLocation(next);
//    addVisitedList(next);
//    eatItem(next);
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

  /**
   * gets all the 8 neighbours iteratively and checks if they are a valid move
   * @return an ArrayList of all the valid neighbours
   */
  public ArrayList<Location> getNeighbourhood() {
    int x = getX();
    int y = getY();
    ArrayList<Location> neighbourhood = new ArrayList<>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        if (Math.abs(i) == Math.abs(j)){
          continue;
        }
        Location location = new Location(x + i, y + j);
        // Exclude The Current Location
        if (canMove(location)) {
          neighbourhood.add(location);
        }
      }
    }
    return neighbourhood;
  }



  /**
   * Gets the next best move based on shortest distance to the target location
   * @param targetLocation the target that we want to move to
   * @return an ArrayList of the best moves based on distance to the target
   */
  public ArrayList<Location> getNextMoves(Location targetLocation) {
    ArrayList<Location> candidates = new ArrayList<>();
    double shortestDistance = Double.MAX_VALUE;

    // Check Each Neighbor Location
    for (Location neighbor : getNeighbourhood()) {

      // Check For Walls
      double distance = neighbor.getDistanceTo(targetLocation);

      if (distance < shortestDistance) {
        candidates.clear();
        candidates.add(neighbor);
        shortestDistance = distance;

      } else if (distance == shortestDistance) {
        candidates.add(neighbor);
      }
    }

    return candidates;
  }

}

