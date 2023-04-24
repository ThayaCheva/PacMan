// PacGrid.java
// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;

import java.awt.*;
import java.util.ArrayList;

public class PacManGameGrid extends GameGrid
{
  private int nbHorzCells;
  private int nbVertCells;
  private int[][] mazeArray;

  private Game game;

  private GridManager gridManager;

  private ArrayList<Location> pillAndItemLocations = new ArrayList<Location>();
  private ArrayList<Location> propertyPillLocations = new ArrayList<>();
  private ArrayList<Location> propertyGoldLocations = new ArrayList<>();

  private ArrayList<Location> goldPillLocation= new ArrayList<Location>();;
  private ArrayList<Location> iceLocations = new ArrayList<Location>();

  public PacManGameGrid(Game game, int nbHorzCells, int nbVertCells)
  {
    this.nbHorzCells = nbHorzCells;
    this.nbVertCells = nbVertCells;
    this.game = game;
    gridManager = new GridManager(game);
    mazeArray = new int[nbVertCells][nbHorzCells];
    String maze =
                    "xxxxxxxxxxxxxxxxxxxx" + // 0
                    "x....x....g...x....x" + // 1
                    "xgxx.x.xxxxxx.x.xx.x" + // 2
                    "x.x.......i.g....x.x" + // 3
                    "x.x.xx.xx  xx.xx.x.x" + // 4
                    "x......x    x......x" + // 5
                    "x.x.xx.xxxxxx.xx.x.x" + // 6
                    "x.x......gi......x.x" + // 7
                    "xixx.x.xxxxxx.x.xx.x" + // 8
                    "x...gx....g...x....x" + // 9
                    "xxxxxxxxxxxxxxxxxxxx";// 10


    // Copy structure into integer array
    for (int i = 0; i < nbVertCells; i++) {
      for (int k = 0; k < nbHorzCells; k++) {
        int value = toInt(maze.charAt(nbHorzCells * i + k));
        mazeArray[i][k] = value;
      }
    }
  }

  private int toInt(char c)
  {
    if (c == 'x')
      return 0;
    if (c == '.')
      return 1;
    if (c == ' ')
      return 2;
    if (c == 'g')
      return 3;
    if (c == 'i')
      return 4;
    return -1;
  }

  public void drawGrid(GGBackground bg)
  {
    bg.clear(Color.gray);
    bg.setPaintColor(Color.white);
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        bg.setPaintColor(Color.white);
        Location location = new Location(x, y);
        int a = getCell(location);
        if (a > 0)
          bg.fillCell(location, Color.lightGray);
        if (a == 1 && this.propertyPillLocations.size() == 0) { // Pill
          gridManager.putPill(bg, location);
        } else if (a == 3 && this.propertyGoldLocations.size() == 0) { // Gold
          gridManager.putGold(bg, location);
          goldPillLocation.add(location);
        } else if (a == 4) {
          gridManager.putIce(bg, location);
          iceLocations.add(location);
        }
      }
    }

    for (Location location : this.propertyPillLocations) {
      gridManager.putPill(bg, location);
    }

    for (Location location : this.propertyGoldLocations) {
      gridManager.putGold(bg, location);
      goldPillLocation.add(location);
    }
  }

  public int countPillsAndItems() {
    int pillsAndItemsCount = 0;
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = getCell(location);
        if (a == 1 && propertyPillLocations.size() == 0) { // Pill
          pillsAndItemsCount++;
        } else if (a == 3 && propertyGoldLocations.size() == 0) { // Gold
          pillsAndItemsCount++;
        }
      }
    }
    if (propertyPillLocations.size() != 0) {
      pillsAndItemsCount += propertyPillLocations.size();
    }

    if (propertyGoldLocations.size() != 0) {
      pillsAndItemsCount += propertyGoldLocations.size();
    }
    return pillsAndItemsCount;
  }

  public void setupPillAndItemsLocations() {
    for (int y = 0; y < nbVertCells; y++)
    {
      for (int x = 0; x < nbHorzCells; x++)
      {
        Location location = new Location(x, y);
        int a = getCell(location);
        if (a == 1 && propertyPillLocations.size() == 0) {
          pillAndItemLocations.add(location);
        }
        if (a == 3 &&  propertyGoldLocations.size() == 0) {
          pillAndItemLocations.add(location);
        }
        if (a == 4) {
          pillAndItemLocations.add(location)  ;
        }
      }
    }

    if (propertyPillLocations.size() > 0) {
      for (Location location : propertyPillLocations) {
        pillAndItemLocations.add(location);
      }
    }
    if (propertyGoldLocations.size() > 0) {
      for (Location location : propertyGoldLocations) {
        pillAndItemLocations.add(location);
      }
    }
  }

  public int getCell(Location location)
  {
    return mazeArray[location.y][location.x];
  }

  public ArrayList<Location> getPillAndItemLocations() {
    return this.pillAndItemLocations;
  }

  public ArrayList<Location> getPropertyPillLocations() {
    return this.propertyPillLocations;
  }

  public ArrayList<Location> getPropertyGoldLocations() {
    return this.propertyGoldLocations;
  }

  public ArrayList<Location> getGoldLocation(){
    return goldPillLocation;
  }

  public GridManager getGridManager() {
    return gridManager;
  }
}