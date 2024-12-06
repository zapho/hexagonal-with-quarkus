/*
 *                C O P Y R I G H T  (c) 2015
 *                        DEDALUS SPA
 *                    All Rights Reserved
 *
 *
 *      THIS IS UNPUBLISHED PROPRIETARY SOURCE CODE OF
 *                        DEDALUS SPA
 *     The copyright notice above does not evidence any
 *    actual or intended publication of such source code.
 */
package org.acme.rebelrescue.fleet;

import org.acme.rebelrescue.fleet.api.AssembleAFleet;
import org.acme.rebelrescue.fleet.spi.StarshipInventory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FleetAssembler implements AssembleAFleet {

    private final StarshipInventory starshipInventory;

    public FleetAssembler(StarshipInventory starshipInventory) {
        this.starshipInventory = starshipInventory;
    }

    @Override
    public Fleet forPassengers(int numberOfPassengers) {
        List<Starship> starships = stashipsWithPassengerCapacity();
        List<Starship> rescueStarships = selectRescueStarships(numberOfPassengers, starships);
        return new Fleet(rescueStarships);
    }

    private List<Starship> selectRescueStarships(int numberOfPassengers, List<Starship> starships) {
        List<Starship> candidateStarships = new ArrayList<>(starships);
        List<Starship> rescueStarships = new ArrayList<>();
        while (numberOfPassengers > 0) {
            Starship starship = candidateStarships.removeFirst();
            rescueStarships.add(starship);
            numberOfPassengers -= starship.capacity();
        }
        return rescueStarships;
    }

    private List<Starship> stashipsWithPassengerCapacity() {
        return starshipInventory.starships()
                .stream()
                .filter(s -> s.capacity() > 0)
                .sorted(Comparator.comparingInt(Starship::capacity))
                .toList();
    }
}
