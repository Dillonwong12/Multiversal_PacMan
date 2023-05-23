// PacMan.java
// Simple PacMan implementation
/**
 * Controls the setup and flow of a game of PacMan.
 */
package src.game;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.GGBackground;
import ch.aplu.jgamegrid.GameGrid;
import ch.aplu.jgamegrid.Location;
import org.jdom.JDOMException;
import src.game.utility.GameCallback;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class Game extends GameGrid
{
  private final static int NB_HORZ_CELLS = 20;
  private final static int NB_VERT_CELLS = 11;

  private int maxScore = 0;
  protected PacManGameGrid grid = new PacManGameGrid(NB_HORZ_CELLS, NB_VERT_CELLS);
  protected PacActor pacActor = new PacActor(this);
  private ArrayList<Monster> monsters = new ArrayList<Monster>();
  private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
  private HashMap<Location, Item> itemLocations = new HashMap<>();
  private GameCallback gameCallback;
  private Properties properties;
  private int seed = 30006;
  private ArrayList<Location> propertyPillLocations = new ArrayList<>();
  private ArrayList<Location> propertyGoldLocations = new ArrayList<>();
  private String gameVersion;
  private GameLoaderHandler gameLoaderHandler;
  public Game(GameCallback gameCallback, Properties properties, File mapXML) throws IOException, JDOMException {


    // Setup game
    super(NB_HORZ_CELLS, NB_VERT_CELLS, 20, false);

    // change the grid to file grid
    if(mapXML != null){
      grid = new PacManGameGrid(NB_HORZ_CELLS, NB_VERT_CELLS, mapXML);
    }

    this.gameCallback = gameCallback;
    this.properties = properties;
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");

    // Get the game version
    gameVersion = properties.getProperty("version");

    // loads the initial game state
    gameLoaderHandler = new GameLoaderHandler(this, properties, seed);
    gameLoaderHandler.setupItemsLocations();

    GGBackground bg = getBg();
    drawGrid(bg);

    gameLoaderHandler.loadCharacters();

    //Run the game
    doRun();
    show();

    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit;
    boolean hasPacmanEatAllPills;

    do {
      hasPacmanBeenHit = false;
      for(Monster monster : monsters){
        Location location = monster.getLocation();
        if (pacActor.getLocation().equals(location)) {
            hasPacmanBeenHit = true;
            break;
        }
        Color c = bg.getColor(location);
        if (c.equals(Portal.PORTAL_COLOR)){
          for (Location loc: itemLocations.keySet()) {
            if (loc.equals(location)) {
              Item item = itemLocations.get(loc);
              Portal p = (Portal)item;
              p.teleport(monster);
            }
          }
        }
      }
      hasPacmanEatAllPills = pacActor.getScore() >= maxScore;
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);

    Location loc = pacActor.getLocation();
    setMonstersStopMove(true);
    pacActor.removeSelf();

    // Check for win or lose
    String title = "";
    if (hasPacmanBeenHit) {
      bg.setPaintColor(Color.red);
      title = "GAME OVER";
      addActor(new Actor("sprites/explosion3.gif"), loc);
    } else if (hasPacmanEatAllPills) {
      bg.setPaintColor(Color.yellow);
      title = "YOU WIN";
    }
    setTitle(title);
    gameCallback.endOfGame(title);

    doPause();
  }
  // For Default Game
  public Game(GameCallback gameCallback, Properties properties) throws IOException, JDOMException {
    this(gameCallback, properties, null);
  }

  /**
   * Draws the initial game grid, while calculating the `maxScore`.
   * @param bg The background
   */
  private void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);

    for (int y = 0; y < NB_VERT_CELLS; y++)
    {
      for (int x = 0; x < NB_HORZ_CELLS; x++)
      {
        Location location = new Location(x, y);
        int a = grid.getCell(location);
        if (a != 1)
          bg.fillCell(location, Color.lightGray);
      }
    }

    // Draws all Items on the background, and add them as Actors. Calculates `maxScore` as it progresses.
    for (Map.Entry<Location, Item> entry: itemLocations.entrySet()){
      entry.getValue().putItem(bg, toPoint(entry.getKey()));
      addActor(entry.getValue(), entry.getKey());
      maxScore += entry.getValue().getPoints();
    }
  }

  /**
   * Removes an Item when PacActor eats it. Adjusts PacActor's `score` and applies itemEffect()s if necessary.
   * @param location The location of the Item being eaten
   */
  public void removeItem(Location location){

    // Remove the item at `location`, incrementing PacActor's `score`
    for (Location loc: itemLocations.keySet()){
      if (loc.equals(location)){
        Item item = itemLocations.get(loc);

        pacActor.setScore(pacActor.getScore() + item.getPoints());
        gameCallback.pacManEatPillsAndItems(location, item.getType());
        item.hide();
        itemLocations.remove(loc);
        break;
      }
    }
  }

  public int getNumHorzCells(){
    return this.NB_HORZ_CELLS;
  }

  public int getNumVertCells(){
    return this.NB_VERT_CELLS;
  }

  public HashMap<Location, Item> getItemLocations(){
    return this.itemLocations;
  }

  public ArrayList<Location> getPillAndItemLocations() {
    return pillAndItemLocations;
  }

  public GameCallback getGameCallback() {
    return gameCallback;
  }

  private void setMonstersStopMove(boolean stopMove){
    for (Monster monster : monsters){
      monster.setStopMoving(stopMove);
    }
  }

  public PacActor getPacActor() {
    return pacActor;
  }

  public void setSeed(int seed) {
    this.seed = seed;
  }

  public PacManGameGrid getGrid() {
    return grid;
  }

  public ArrayList<Monster> getMonsters() {
    return monsters;
  }

  public Properties getProperties() {
    return properties;
  }

  public int getSeed() {
    return seed;
  }
}
