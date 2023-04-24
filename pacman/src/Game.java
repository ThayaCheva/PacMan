// PacMan.java
// Simple PacMan implementation
// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;
import src.utility.GameCallback;

import java.awt.*;
import java.util.ArrayList;
import java.util.Properties;

public class Game extends GameGrid
{
  private final static int nbHorzCells = 20;
  private final static int nbVertCells = 11;
  protected PacManGameGrid grid = new PacManGameGrid(this, nbHorzCells, nbVertCells);

  protected PacActor pacActor = new PacActor(this);
  private Troll troll = new Troll(this);
  private Tx5 tx5 = new Tx5(this);
  private Alien alien = new Alien(this);
  private Orion orion = new Orion(this);
  private Wizard wizard = new Wizard(this);
  private ArrayList<Monster> monsterActors = new ArrayList<Monster>();

  private int seed = 30006;
  private GameCallback gameCallback;
  private Properties properties;

  public Game(GameCallback gameCallback, Properties properties)
  {
    //Setup game
    super(nbHorzCells, nbVertCells, 20, false);
    this.gameCallback = gameCallback;
    this.properties = properties;
    setSimulationPeriod(100);
    setTitle("[PacMan in the Multiverse]");

    //Setup for auto test
    pacActor.setPropertyMoves(properties.getProperty("PacMan.move"));
    pacActor.setAuto(Boolean.parseBoolean(properties.getProperty("PacMan.isAuto")));
    loadPillAndItemsLocations();

    //Draws the grid
    GGBackground bg = getBg();
    grid.drawGrid(bg);

    orion.setGoldPillLocation(grid.getGoldLocation());

    //Setup Random seeds
    seed = Integer.parseInt(properties.getProperty("seed"));
    monsterActors.add(troll);
    monsterActors.add(tx5);
    pacActor.setSeed(seed);
    // Checks the version of the game
    if (properties.getProperty("version").equals("multiverse")) {
      monsterActors.add(alien);
      monsterActors.add(orion);
      monsterActors.add(wizard);
    }

    // Set seed for all monsters
    for (Monster monster : monsterActors) {
      monster.setSeed(seed);
    }
    addKeyRepeatListener(pacActor);
    setKeyRepeatPeriod(150);

    // Set slowdown for all monsters
    for (Monster monster : monsterActors) {
      monster.setSlowDown(3);
    }

    pacActor.setSlowDown(3);
    tx5.stopMoving(5);
    setupActorLocations();

    //Run the game
    doRun();
    show();
    // Loop to look for collision in the application thread
    // This makes it improbable that we miss a hit
    boolean hasPacmanBeenHit;
    boolean hasPacmanEatAllPills;
    grid.setupPillAndItemsLocations();
    int maxPillsAndItems = grid.countPillsAndItems();

    // Check if game ends
    do {
      hasPacmanBeenHit = false;
      // Check collision between all monsters and pacman
      for (Monster monster : monsterActors) {
        if (monster.getLocation().equals(pacActor.getLocation())) {
          hasPacmanBeenHit = true;
        }
      }
      hasPacmanEatAllPills = pacActor.getNbPills() >= maxPillsAndItems;
      delay(10);
    } while(!hasPacmanBeenHit && !hasPacmanEatAllPills);
    delay(120);
    Location loc = pacActor.getLocation();

    // Stop all monster movement when game ends
    for (Monster monster : monsterActors) {
      monster.setStopMoving(true);
    }

    pacActor.removeSelf();

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

  public void loadPillAndItemsLocations() {
    // Load Pill location from properties file
    String pillsLocationString = properties.getProperty("Pills.location");
    if (pillsLocationString != null) {
      String[] singlePillLocationStrings = pillsLocationString.split(";");
      for (String singlePillLocationString: singlePillLocationStrings) {
        String[] locationStrings = singlePillLocationString.split(",");
        grid.getPropertyPillLocations().add(new Location(Integer.parseInt(locationStrings[0]), Integer.parseInt(locationStrings[1])));
      }
    }

    // Load Gold location from properties file
    String goldLocationString = properties.getProperty("Gold.location");
    if (goldLocationString != null) {
      String[] singleGoldLocationStrings = goldLocationString.split(";");
      for (String singleGoldLocationString: singleGoldLocationStrings) {
        String[] locationStrings = singleGoldLocationString.split(",");
        grid.getPropertyGoldLocations().add(new Location(Integer.parseInt(locationStrings[0]), Integer.parseInt(locationStrings[1])));
      }
    }
  }

  private void setupActorLocations() {
    for (Monster monster : monsterActors) {
      String[] locations = this.properties.getProperty(monster.getType() + ".location").split(",");
      int locationX = Integer.parseInt(locations[0]);
      int locationY = Integer.parseInt(locations[1]);

      addActor(monster, new Location(locationX, locationY), Location.NORTH);
    }

    String[] pacManLocations = this.properties.getProperty("PacMan.location").split(",");

    int pacManX = Integer.parseInt(pacManLocations[0]);
    int pacManY = Integer.parseInt(pacManLocations[1]);

    addActor(pacActor, new Location(pacManX, pacManY));
  }

  // Function to add items to actor
  public void addItem(Actor actor, Location location) {
    addActor(actor, location);
  }

  public GameCallback getGameCallback() {
    return this.gameCallback;
  }

  public int getNumHorzCells(){
    return this.nbHorzCells;
  }

  public int getNumVertCells(){
    return this.nbVertCells;
  }

  public PacManGameGrid getGameGrid() {
    return grid;
  }

  public ArrayList<Monster> getMonsterActor() {
    return monsterActors;
  }

  public Properties getProperties() { return this.properties; }
}