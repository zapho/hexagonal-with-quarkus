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
package org.acme.rebelrescue.fleet.spi.stubs;

import org.acme.ddd.Stub;
import org.acme.rebelrescue.fleet.Starship;
import org.acme.rebelrescue.fleet.spi.StarshipInventory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Stub
public class StarshipInventoryStub implements StarshipInventory {
    private static final Logger LOG = LogManager.getLogger(StarshipInventoryStub.class);

    private final List<Starship> starships;

    private static final List<Starship> DEFAULT_STARSHIPS = List.of(
            new Starship("X-Wing", 0),
            new Starship("Millennium Falcon", 6),
            new Starship("Rebel transport", 90),
            new Starship("Mon Calamari Star Cruisers", 1200),
            new Starship("CR90 corvette", 600),
            new Starship("EF76 Nebulon-B escort frigate", 800));

    public StarshipInventoryStub(List<Starship> starships) {
        LOG.info("Using stub starship inventory");
        this.starships = starships;
    }

    public StarshipInventoryStub() {
        this(DEFAULT_STARSHIPS);
    }

    @Override
    public List<Starship> starships() {
        return starships;
    }
}
