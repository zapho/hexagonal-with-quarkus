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
package org.acme.rebelrescue.app.base.control;

import org.acme.rebelrescue.app.base.boundary.CreateRebelBaseRequest;
import org.acme.rebelrescue.app.base.boundary.RebelBaseResource;
import org.acme.rebelrescue.app.base.entity.RebelBaseEntity;
import org.acme.rebelrescue.base.RebelBase;
import org.acme.rebelrescue.base.RiskLevel;
import org.acme.rebelrescue.fleet.Fleet;
import org.acme.rebelrescue.fleet.Starship;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Mapper(componentModel = "cdi")
public interface RebelBaseMapper {

    RebelBase toRebelBaseDomain(RebelBaseEntity rebelBaseDto);
    RebelBase toRebelBaseDomain(CreateRebelBaseRequest createRebelBaseRequest);

    default RiskLevel toRiskLevelDomain(String level) {
        for (RiskLevel riskLevel : RiskLevel.values()) {
            if (riskLevel.name().equalsIgnoreCase(level)) {
                return riskLevel;
            }
        }
        return RiskLevel.COMPROMISED;
    }

    default RiskLevel toRiskLevelDomain(int level) {
        RiskLevel riskLevel = RiskLevel.COMPROMISED;
        if (level <= RiskLevel.SECURE.getLevel()) {
            riskLevel = RiskLevel.SECURE;
        } else if (level <= RiskLevel.CAUTIOUS.getLevel()) {
            riskLevel = RiskLevel.CAUTIOUS;
        } else if (level <= RiskLevel.ELEVATED.getLevel()) {
            riskLevel = RiskLevel.ELEVATED;
        } else if (level <= RiskLevel.CRITICAL.getLevel()) {
            riskLevel = RiskLevel.CRITICAL;
        }
        return riskLevel;
    }

    default Fleet toFleetDomain(List<String> starshipNames) {
        return new Fleet(starshipNames.stream().map(this::toStarshipDomain).toList());
    }

    default Starship toStarshipDomain(String name) {
        return new Starship(name, 0, BigDecimal.ZERO);
    }

    List<RebelBaseResource> toRebelBaseResources(List<RebelBase> rebelBase);

    RebelBaseResource toRebelBaseResource(RebelBase rebelBase);
}
