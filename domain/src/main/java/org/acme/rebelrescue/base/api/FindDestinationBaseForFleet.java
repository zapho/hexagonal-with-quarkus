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
package org.acme.rebelrescue.base.api;

import org.acme.rebelrescue.base.RebelBase;
import org.acme.rebelrescue.base.RiskLevel;
import org.acme.rebelrescue.fleet.Fleet;

import java.time.Duration;
import java.util.List;

public interface FindDestinationBaseForFleet {
    List<RebelBase> findRebelBases(Fleet fleet, RiskLevel maxRiskLevel, Duration travelDuration);
}