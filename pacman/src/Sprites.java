// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class Sprites extends Actor {
    public Game game;
    public ArrayList<Location> visitedList = new ArrayList<Location>();
    public int seed;
    public Random randomiser;
    public int listLength = 10;

    // Monster Sprite
    public Sprites(Game game, String type) {
        super(type);
        this.game = game;
    }

    // PacMan Sprite
    public Sprites(Game game, String filename, boolean isRotatable, int nbSprites) {
        super(isRotatable, filename, nbSprites);
        this.game = game;
    }
    public abstract void act();

    public void addVisitedList(Location location)
    {
        visitedList.add(location);
        if (visitedList.size() == listLength)
            visitedList.remove(0);
    }

    public boolean isVisited(Location location)
    {
        for (Location loc : visitedList)
            if (loc.equals(location))
                return true;
        return false;
    }

    public boolean canMove(Location location)
    {
        Color c = getBackground().getColor(location);
        if ( c.equals(Color.gray) || location.getX() >= game.getNumHorzCells()
                || location.getX() < 0 || location.getY() >= game.getNumVertCells() || location.getY() < 0)
            return false;
        else
            return true;
    }

    public void setSeed(int seed) {
        this.seed = seed;
        randomiser.setSeed(seed);
    }
    public void setVisitedLengthList(int length){
        this.listLength=length;
    }
}
