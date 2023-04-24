// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202
package src;
import ch.aplu.jgamegrid.*;

// Interface for when pacman consumes pills
public interface Consumable {
    void consume(Game game,PacActor pacman,Location location);
}
