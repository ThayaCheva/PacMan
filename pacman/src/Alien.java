// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Collections;

public class Alien extends Monster {
    public Alien(Game game) {
        super(game, "sprites/m_alien.gif", "Alien");
    }

    protected void walkApproach()
    {
        Location pacLocation = game.pacActor.getLocation();
        Location monLocation = getLocation();
        ArrayList<Integer> distanceToPac = new ArrayList<Integer>();
        ArrayList<Location> neighborLocations = new ArrayList<Location>();

        // Calculate all the neighboring location of the Alien
        for (int i = 0; i < 360; i += 45) {
            Location neighbor = monLocation.getNeighbourLocation(i);
            if (canMove(neighbor)) {
                int distance = neighbor.getDistanceTo(pacLocation);
                distanceToPac.add(distance);
                neighborLocations.add(neighbor);
            }
        }

        // Find the shortest distance between Pacman
        ArrayList<Location> shortestLocation = new ArrayList<Location>();
        int min_distance = Collections.min(distanceToPac);
        for (int i = 0; i < neighborLocations.size(); i++) {
            if (neighborLocations.get(i).getDistanceTo(pacLocation) == min_distance) {
                shortestLocation.add(neighborLocations.get(i));
            }
        }

        // Randomize multiple shortests distance
        Location shortest;
        if (shortestLocation.size() > 1) {
            Collections.shuffle(shortestLocation);
        }
        shortest = shortestLocation.get(0);

        // Walking approach:
        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(pacLocation);

        setDirection(compassDir);
        // Random walk
        int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;

        turn(sign * 90);  // Try to turn left/right
        Location next = shortest;

        if(getFurious()){
            next = furious(next,getLocation().get4CompassDirectionTo(next));
        }

        setLocation(next);

        game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }
}

