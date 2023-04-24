// Monster.java
// Used for PacMan
// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import ch.aplu.jgamegrid.*;

public abstract class  Monster extends Sprites implements FuriousBehaviour
{
  private boolean stopMoving = false;
  public String type;
  private boolean isFurious = false;
  private boolean isFrozen;

  public Monster(Game game, String filename, String type)
  {
    super(game, filename);
    this.randomiser = new Random(0);
    this.type = type;
    setSeed(0);
  }

  public void stopMoving(int seconds) {
    this.setFrozen(true);
    this.stopMoving = true;
    Timer timer = new Timer(); // Instantiate Timer Object
    int SECOND_TO_MILLISECONDS = 1000;
    final Monster monster = this;
    // Set timer for monster to stop moving
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        monster.stopMoving = false;
      }
    }, seconds * SECOND_TO_MILLISECONDS);

    // Set timer for monster being frozen
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        setFrozen(false);
      }
    }, seconds * SECOND_TO_MILLISECONDS);
  }

  public void setSeed(int seed) {
    this.seed = seed;
    randomiser.setSeed(seed);
  }

  public void setStopMoving(boolean stopMoving) {
    this.stopMoving = stopMoving;
  }

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

  protected abstract void walkApproach();

  public String getType() {
    return type;
  }

  // Make the monster furious when gold pill is eaten
  public void makeFurious() {
    Timer timer = new Timer();
    int SECOND_TO_MILLISECONDS = 1000;
    // Monsters can only be furious when not frozen
    if(!getFrozen()) {
      setFurious(true);
      // Set timer for monster to stop being furious
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          setFurious(false);
        }
      }, 3 * SECOND_TO_MILLISECONDS);
    }
  }

  // Function to check adjacent cell when monster is furious
  public Location furious(Location curr, Location.CompassDirection direction){
    Location adjacent = curr.getAdjacentLocation(direction,1);
    if (canMove(adjacent)) {
      return adjacent;
    }
    return curr;
  }

  // Function to check adjacent cell when monster is furious
  public Location furious(Location curr, double direction){
    Location adjacent = curr.getAdjacentLocation(direction,1);
    if (canMove(adjacent)) {
      return adjacent;
    }
    return curr;
  }

  public void setFurious(boolean isFurious) {
    this.isFurious = isFurious;
  }

  public boolean getFurious() {
    return this.isFurious;
  }

  public void setFrozen(boolean isFrozen) { this.isFrozen = isFrozen; }

  public boolean getFrozen() {
    return this.isFrozen;
  }
}
