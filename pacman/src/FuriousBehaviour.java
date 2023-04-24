// Team 03
// Rohit Sandeep Ambakkat 1200197
// Thaya Chevaphatrakul 1167144
// Tran Than Han Ha 1472202

package src;

import ch.aplu.jgamegrid.Location;

// Interface to manage monsters' furious behaviour
public interface FuriousBehaviour {
    Location furious(Location curr, Location.CompassDirection direction);
    Location furious(Location curr, double direction);
}
