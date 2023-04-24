// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;

public class Orion extends Monster {
    private ArrayList<Location> goldPillLocation;
    private Location target= new Location(-1,-1);
    private ArrayList<Location> visitedGold=new ArrayList<Location>();
    public Orion(Game game) {
        super(game, "sprites/m_orion.gif", "Orion");
        setVisitedLengthList(20);
    }

    protected void walkApproach() {
        ItemType goal = ItemType.Gold;

        // Check if orion has visited its selected gold
        if(getLocation().equals(target)){
            visitedGold.add(target);
            visitedList = new ArrayList<Location>();
            target = new Location(-1,-1);
        }

        // When all gold pieces have been visited, Orion will start over again
        if(visitedGold.size() == goldPillLocation.size()) {
            visitedGold = new ArrayList<Location>();
            visitedList = new ArrayList<Location>();
        }

        // Select new gold to target
        if(target.equals(new Location(-1,-1))){
            int found = 0;
            Collections.shuffle(goldPillLocation);
            // Select random target
            for (int i = 0; i < goldPillLocation.size(); i++) {
                Color c = getBackground().getColor(goldPillLocation.get(i));
                if(!visitedGold.contains(goldPillLocation.get(i)) && c.equals(goal.getColor()) ){
                    target = goldPillLocation.get(i);
                    found = 1;
                    break;
                }
            }

            // If all gold eaten, target unvisited gold location
            if (found == 0) {
                for (int i = 0; i < goldPillLocation.size(); i++) {
                    if (!visitedGold.contains(goldPillLocation.get(i))) {
                        target = goldPillLocation.get(i);
                        break;
                    }
                }
            }
        }

        double oldDirection = getDirection();

        // Walking approach:
        // Orion uses TX5 walking approach but instead of using pacMan location, it uses gold location
        Location.CompassDirection compassDir = getLocation().get4CompassDirectionTo(target);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);

        if (!isVisited(next) && canMove(next))
        {
            if(getFurious()){ // If furious, check adjacent cell
                next = furious(next,compassDir);
            }
            setLocation(next);
        }
        else
        {
            // Random walk
            int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
            setDirection(oldDirection);
            turn(sign * 90);  // Try to turn left/right
            next = getNextMoveLocation();
            if (canMove(next))
            {
                if(getFurious()){ // If furious, check adjacent cell
                    next = furious(next,getDirection());
                }
                setLocation(next);
            }
            else
            {
                setDirection(oldDirection);
                next = getNextMoveLocation();
                if (canMove(next)) // Try to move forward
                {
                    if(getFurious()){ // If furious, check adjacent cell
                        next = furious(next,getDirection());
                    }
                    setLocation(next);
                }
                else
                {
                    setDirection(oldDirection);
                    turn(-sign * 90);  // Try to turn right/left
                    next = getNextMoveLocation();
                    if (canMove(next))
                    {
                        if(getFurious()){ // If furious, check adjacent cell
                            next = furious(next,getDirection());
                        }
                        setLocation(next);
                    }
                    else
                    {

                        setDirection(oldDirection);
                        turn(180);  // Turn backward
                        next = getNextMoveLocation();
                        if (getFurious()) { // If furious, check adjacent cell
                            next = furious(next,getDirection());
                        }
                        setLocation(next);
                    }
                }
            }
        }
        game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }

    public void setGoldPillLocation(ArrayList<Location> goldPillLocation){
        this.goldPillLocation=new ArrayList<Location>(goldPillLocation);
    }

    public void setVisitedLengthList(int length){
        this.listLength=length;
    }
}
