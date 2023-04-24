// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;

import java.awt.Color;

public enum ItemType implements Consumable {
    Gold{
        @Override
        public void consume(Game game,PacActor pacman,Location location) {
            // Add Score
            pacman.setNbPills(pacman.getNbPills()+1);
            pacman.setScore(pacman.getScore()+5);
            game.getGameCallback().pacManEatPillsAndItems(location, "gold");
            game.getGameGrid().getGridManager().removeItem("gold",location);
            // Make monsters furious when consumed
            if(game.getProperties().getProperty("version").equals("multiverse")) {
                for (Monster mon : game.getMonsterActor()) {
                    mon.makeFurious();
                }
            }
        }
    },
    Ice{
        @Override
        public void consume(Game game,PacActor pacman,Location location) {
            game.getGameCallback().pacManEatPillsAndItems(location, "ice");
            game.getGameGrid().getGridManager().removeItem("ice",location);
            // Make monster freeze when eaten
            if(game.getProperties().getProperty("version").equals("multiverse")) {
                for (Monster monster : game.getMonsterActor()) {
                    monster.stopMoving(3);
                }
            }
        }
    },

    Pill{
        @Override
        public void consume(Game game,PacActor pacman,Location location ) {
            // Add score when consumed
            pacman.setNbPills(pacman.getNbPills()+1);
            pacman.setScore(pacman.getScore()+1);
            game.getGameCallback().pacManEatPillsAndItems(location, "pills");
        }
    };

    public Color getColor() {
        switch (this) {
            case Gold: return Color.yellow ;
            case Ice: return Color.blue;
            case Pill: return Color.white ;
            default: {
                assert false;
            }
        }
        return null;
    }

    public String getImageFile(){
        switch (this) {
            case Gold: return "sprites/gold.png";
            case Ice: return "sprites/ice.png";
            default: {
                assert false;
            }
        }
        return null;
    }

}