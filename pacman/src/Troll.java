// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;

import ch.aplu.jgamegrid.*;

public class Troll extends Monster {
    public Troll(Game game) {
        super(game, "sprites/m_troll.gif", "Troll");
    }

    protected void walkApproach()
    {
        Location pacLocation = game.pacActor.getLocation();
        double oldDirection = getDirection();

        // Walking approach:
        // TX5: Determine direction to pacActor and try to move in that direction. Otherwise, random walk.
        // Troll: Random walk.
        Location.CompassDirection compassDir =
                getLocation().get4CompassDirectionTo(pacLocation);
        Location next = getLocation().getNeighbourLocation(compassDir);
        setDirection(compassDir);
        // Random walk
        int sign = randomiser.nextDouble() < 0.5 ? 1 : -1;
        setDirection(oldDirection);
        turn(sign * 90);  // Try to turn left/right
        next = getNextMoveLocation();
        if (canMove(next))
        {
            if (getFurious()) { // If furious, check adjacent cell
                next = furious(next,getDirection());
            }
            setLocation(next);

        }
        else {
            setDirection(oldDirection);
            next = getNextMoveLocation();
            if (canMove(next)) // Try to move forward
            {
                if(getFurious()){ // If furious, check adjacent cell
                    next = furious(next,getDirection());
                }
                setLocation(next);

            } else {
                setDirection(oldDirection);
                turn(-sign * 90);  // Try to turn right/left
                next = getNextMoveLocation();
                if (canMove(next)) {
                    if (getFurious()) {
                        next = furious(next,getDirection());
                    }
                    setLocation(next);

                } else {

                    setDirection(oldDirection);
                    turn(180);  // Turn backward
                    next = getNextMoveLocation();
                    if (getFurious()) {
                        next = furious(next,getDirection());
                    }
                    setLocation(next);
                }
            }
        }
        game.getGameCallback().monsterLocationChanged(this);
        addVisitedList(next);
    }
}