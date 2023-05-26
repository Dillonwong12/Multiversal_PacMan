// Monster.java
// Used for PacMan
/**
 * Subclass of Character. Monsters all move with different walkApproaches().
 */
package src.game;

/**
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Monster extends Character
{
  private MonsterType type;
  private static final int SECONDS_TO_MILLISECONDS = 1000;
  private boolean stopMoving = false;
  private boolean isFurious = false;

  public Monster(String sprite, Game game, MonsterType type)
  {
    super(sprite, game);
    this.type = type;
  }

  /**
   * Causes a Monster to stop moving for a period of `seconds` by toggling `stopMoving`.
   * @param seconds The duration to cease moving
   */
  public void stopMoving(int seconds) {
    this.stopMoving = true;
    // Instantiate Timer Object
    Timer timer = new Timer();
    final Monster monster = this;
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.stopMoving = false;
      }
    }, seconds * SECONDS_TO_MILLISECONDS);
  }

  /**
   * Calls each Monster's walkApproach().
   */
  @Override
  public void act()
  {
    if (stopMoving) {
      return;
    }
    walkApproach();
    if (getDirection() > 150 && getDirection() < 210)
      setHorzMirror(false);
    else
      setHorzMirror(true);
  }

  /**
   * Defines the unique movement of each Monster by being overridden in children classes. By default, implements a
   * random walking approach, as this is used by both Troll and TX5. Randomly turns left or right. If it hits a wall, it
   * will return to face the `oldDirection` and go forward, or turn the other side. Otherwise, goes backwards.
   */
  public void walkApproach(){
    // Random walk as default for Troll and TX5
    double oldDirection = getDirection();
    int sign = getRandomiser().nextDouble() < 0.5 ? 1 : -1;
    int angles[] = {sign*90, 0, -sign*90, 180};

    // Keep trying to move in each direction
    for (int angle : angles){
      setDirection(oldDirection);
      turn(angle);
      Location next = getNextMoveLocation();
      if (canMove(next)){
        // Try to move two steps in the same direction if a Monster is furious
        if (isFurious()){
          Location doubleStep = next.getNeighbourLocation(getDirection());
          if (canMove(doubleStep)){
            next = doubleStep;
          }
          else {
            continue;
          }
        }
        setLocation(next);
        this.getGame().getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
        return;
      }
    }
  }

  @Override
  public Game getGame() {
    return super.getGame();
  }

  public abstract String getType();

  public boolean stoppedMoving(){
    return this.stopMoving;
  }

  public boolean isFurious(){
    return this.isFurious;
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }

  /**
   * gets all the 8 neighbours of the current Monster iteratively and checks if they are a valid move
   * @return an ArrayList of all the valid neighbours
   */
  public ArrayList<Location> getNeighbourhood() {
    int x = getX();
    int y = getY();
    ArrayList<Location> neighbourhood = new ArrayList<>();
    for (int i = -1; i <= 1; i++) {
      for (int j = -1; j <= 1; j++) {
        Location location = new Location(x + i, y + j);
        // Exclude The Current Location
        if (canMove(location) && !(i == 0 && j == 0)) {
          neighbourhood.add(location);
        }
      }
    }
    return neighbourhood;
  }

  /**
   * Gets the next best move based on shortest distance to the target location
   * if it is furious gets the best move for 2 cells
   * @param targetLocation the target that we want to move to
   * @return an ArrayList of the best moves based on distance to the target
   */
  public ArrayList<Location> getNextMoves(Location targetLocation) {
    ArrayList<Location> candidates = new ArrayList<>();
    double shortestDistance = Double.MAX_VALUE;

    // Check Each Neighbor Location
    for (Location neighbor : getNeighbourhood()) {
      if(isFurious){
        // get the next location for 2 cells
        Location doubleStep = neighbor.getNeighbourLocation(getLocation().getCompassDirectionTo(neighbor));
        if (canMove(doubleStep)) {
          neighbor = doubleStep;
        }
        else{
          continue;
        }
      }


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
