// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;
import java.util.*;

public class Wizard extends Monster {

    public Wizard(Game game) {
        super(game, "sprites/m_wizard.gif", "Wizard");
    }

    protected void walkApproach() {
        Location monLocation = getLocation();
        ArrayList<Location> neighborLocations = new ArrayList<Location>();
        // Find all neighbour cell of wizard
        for (int i = 0; i < 360; i += 45) {
            Location neighbor = monLocation.getNeighbourLocation(i);
            neighborLocations.add(neighbor);
        }
        // randomize the locations
        Collections.shuffle(neighborLocations);
        Location loc = neighborLocations.get(0);
        Location next = getLocation();

        // Walking approach:
        while (next.equals(getLocation())) {
            // If the selected location is a maze wall, it will check if the adjacent
            // location in the same direction as the selected location is a wall or
            // not
            if (!canMove(loc)) {
                Location.CompassDirection currDir = getLocation().getCompassDirectionTo(loc);
                Location adjacent= loc.getAdjacentLocation(currDir, 1);

                //If the adjacent location is not a wall, the Wizard walk
                //through the wall to that adjacent location
                if (canMove(adjacent)) {
                    next = adjacent;
                } else {
                    //If the adjacent location is a wall, Wizard will randomly select
                    //another neighbour location around itself.
                    Collections.shuffle(neighborLocations);
                    loc = neighborLocations.get(0);

                }
            } else {
                //If the selected location is not a maze wall, Wizard will move to that location.
                next = loc;
            }
        }

        if(getFurious()){ // If furious, check adjacent cell
            next = furious(next,getLocation().getCompassDirectionTo(next));
        }

        setLocation(next);

        game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }
}

