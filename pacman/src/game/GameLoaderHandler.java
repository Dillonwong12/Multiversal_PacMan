package src.game;

/**
 * Loads Games
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

import ch.aplu.jgamegrid.Location;

import java.util.Properties;

public class GameLoaderHandler {
    Game game;

    public GameLoaderHandler(Game game , Properties properties, int seed){
        this.game = game;
    }

    public void loadCharacters(){
        // setup for autotest
        game.getPacActor().setAuto(Boolean.parseBoolean(game.getProperties().getProperty("PacMan.isAuto")));
        game.getPacActor().setPillAndGoldLocations(game.getPillAndItemLocations());
        setupActorLocations();
        game.setSeed(Integer.parseInt(game.getProperties().getProperty("seed")));
        setActorsSeed(game.getSeed());

        game.addKeyRepeatListener(game.getPacActor());
        game.setKeyRepeatPeriod(150);
        setActorSlowDown(3);
    }

    /**
     * Sets up Item locations in `itemLocations` and `pillAndItemLocations`.
     */
    public void setupItemsLocations() {
        Portal whitePortal = null;
        Portal yellowPortal = null;
        Portal darkGoldPortal = null;
        Portal darkGrayPortal = null;
        // Collects Locations and corresponding Items. Ignores Pills and Gold if their Auto Test Configurations have
        // been set
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNumHorzCells(); x++)
            {
                Location location = new Location(x, y);
                int a = game.getGrid().getCell(location);
                if (a == 2) {
                    game.getItemLocations().put(location, new Pill());
                    game.getPillAndItemLocations().add(location);
                }
                else if (a == 3) {
                    game.getItemLocations().put(location, new Gold());
                    game.getPillAndItemLocations().add(location);
                }
                else if (a == 4) {
                    game.getItemLocations().put(location, new Ice());
                    game.getPillAndItemLocations().add(location);
                }
                else if (a == 8) {
                    Portal portal = new Portal("pacman/sprites/resizedImages/i_portalWhiteTileResized.png");
                    game.getItemLocations().put(location, portal);
                    if (whitePortal == null){
                        whitePortal = portal;
                    }
                    else {
                        whitePortal.setPairPortal(portal);
                        portal.setPairPortal(whitePortal);
                    }
                }
                else if (a == 9) {
                    Portal portal = new Portal("pacman/sprites/resizedImages/j_portalYellowTileResized.png");
                    game.getItemLocations().put(location, portal);
                    if (yellowPortal == null){
                        yellowPortal = portal;
                    }
                    else {
                        yellowPortal.setPairPortal(portal);
                        portal.setPairPortal(yellowPortal);
                    }
                }
                else if (a == 10) {
                    Portal portal = new Portal("pacman/sprites/resizedImages/k_portalDarkGoldTileResized.png");
                    game.getItemLocations().put(location, portal);
                    if (darkGoldPortal == null){
                        darkGoldPortal = portal;
                    }
                    else {
                        darkGoldPortal.setPairPortal(portal);
                        portal.setPairPortal(darkGoldPortal);
                    }
                }
                else if (a == 11) {
                    Portal portal = new Portal("pacman/sprites/resizedImages/l_portalDarkGrayTileResized.png");
                    game.getItemLocations().put(location, portal);
                    if (darkGrayPortal == null){
                        darkGrayPortal = portal;
                    }
                    else {
                        darkGrayPortal.setPairPortal(portal);
                        portal.setPairPortal(darkGrayPortal);
                    }
                }
            }
        }
    }

    /**
     * Sets the seed for all Actors
     * @param seed integer hyperparameter
     */
    private void setActorsSeed (int seed){
        game.getPacActor().setSeed(seed);
        for (Monster monster : game.getMonsters()){
            monster.setSeed(seed);
        }
    }

    /**
     * Sets the slow down for all Actors
     * @param slowDown integer hyperparameter
     */
    private void setActorSlowDown(int slowDown){
        game.getPacActor().setSlowDown(slowDown);
        for (Monster monster : game.getMonsters()){
            monster.setSlowDown(slowDown);
        }
    }

    private void setupActorLocations() {
        for (int y = 0; y < game.getNumVertCells(); y++)
        {
            for (int x = 0; x < game.getNumHorzCells(); x++)
            {
                Location location = new Location(x, y);
                int a = game.getGrid().getCell(location);
                if (a == 5) {
                    game.addActor(game.getPacActor(), location);
                }
                else if (a == 6) {
                    Troll troll = new Troll(game);
                    game.addActor(troll, location, Location.NORTH);
                    game.getMonsters().add(troll);
                }
                else if (a == 7) {
                    TX5 tx5 = new TX5(game);
                    game.addActor(tx5, location, Location.NORTH);
                    game.getMonsters().add(tx5);
                    tx5.stopMoving(5);
                }
            }
        }
    }

}
