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
package org.acme.rebelrescue.app.fleet.control;

import org.acme.rebelrescue.app.fleet.boundary.FleetResource;
import org.acme.rebelrescue.app.fleet.boundary.StarshipResource;
import org.acme.rebelrescue.fleet.Fleet;
import org.acme.rebelrescue.fleet.Starship;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "cdi")
public interface FleetMapper {
    FleetResource toFleetResource(Fleet fleet);
    @Mapping(target = "capacity", source = "passengersCapacity")
    StarshipResource toStarshipResource(Starship starship);
}
