// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;
import java.util.ArrayList;

public class GridManager extends GameGrid {
    private Game game;
    private ArrayList<Actor> iceCubes = new ArrayList<Actor>();
    private ArrayList<Actor> goldPieces = new ArrayList<Actor>();

    public GridManager(Game game) {
        this.game = game;
    }

    public void putPill(GGBackground bg, Location location){
        bg.fillCircle(toPoint(location), 5);
    }

    public void putGold(GGBackground bg, Location location){
        ItemType goldType = ItemType.Gold;
        bg.setPaintColor(goldType.getColor());
        bg.fillCircle(toPoint(location), 5);
        Actor gold = new Actor(goldType.getImageFile());
        this.goldPieces.add(gold);
        game.addItem(gold, location);
    }

    public void putIce(GGBackground bg, Location location){
        ItemType iceType = ItemType.Ice;
        bg.setPaintColor(iceType.getColor());
        bg.fillCircle(toPoint(location), 5);
        Actor ice = new Actor(iceType.getImageFile());
        this.iceCubes.add(ice);
        game.addItem(ice, location);
    }

    public void removeItem(String type, Location location){
        if (type.equals("gold")) { // Remove gold
            for (Actor item : this.goldPieces) {
                if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
                    item.hide();
                }
            }
        } else if(type.equals("ice")) { // Remove ice
            for (Actor item : this.iceCubes) {
                if (location.getX() == item.getLocation().getX() && location.getY() == item.getLocation().getY()) {
                    item.hide();
                }
            }
        }
    }
}
