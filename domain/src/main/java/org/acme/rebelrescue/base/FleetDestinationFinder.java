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
package org.acme.rebelrescue.base;

import org.acme.ddd.DomainService;
import org.acme.rebelrescue.base.api.FindDestinationBaseForFleet;
import org.acme.rebelrescue.base.spi.BaseRepository;
import org.acme.rebelrescue.fleet.Fleet;
import org.acme.rebelrescue.hyperspace.api.ComputeTravelDuration;

import java.time.Duration;
import java.util.List;

@DomainService
public class FleetDestinationFinder implements FindDestinationBaseForFleet {

    private final BaseRepository repository;
    private final ComputeTravelDuration duration;

    public FleetDestinationFinder(BaseRepository repository, ComputeTravelDuration duration) {
        this.repository = repository;
        this.duration = duration;
    }

    @Override
    public List<RebelBase> findRebelBases(Fleet fleet, RiskLevel riskLevel, Duration travelDuration) {
        if (fleet == null || fleet.starships().isEmpty() || riskLevel == null || travelDuration == null) {
            return List.of();
        }
        List<RebelBase> bases = repository.findBaseByMaxRiskLevel(riskLevel.getLevel());
        return bases
                .stream()
                .filter(base ->
                        duration.to(base.name()).compareTo(travelDuration) < 0
                                && base.shipCapacity() >= fleet.starships().size())
                .toList();
    }
}
