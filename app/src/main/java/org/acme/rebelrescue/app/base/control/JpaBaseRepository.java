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

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.rebelrescue.app.base.entity.RebelBaseEntity;
import org.acme.rebelrescue.base.RebelBase;
import org.acme.rebelrescue.base.spi.BaseRepository;

import java.util.List;

@ApplicationScoped
public class JpaBaseRepository implements BaseRepository {

    @Inject
    RebelBaseMapper rebelBaseMapper;

    @Override
    public List<RebelBase> findBaseByMaxRiskLevel(int riskLevel) {
        return RebelBaseEntity.findByMaxRiskLevel(riskLevel).stream()
                .map(e -> rebelBaseMapper.toRebelBaseDomain(e))
                .toList();
    }

    public void create(RebelBase rebelBase) {
        if (rebelBase == null) {
            return;
        }
        RebelBaseEntity entity = new RebelBaseEntity();
        entity.name = rebelBase.name();
        entity.riskLevel = rebelBase.riskLevel().getLevel();
        entity.shipCapacity = rebelBase.shipCapacity();
        entity.persist();
    }

    public List<RebelBase> findAll() {
        return RebelBaseEntity.findAll().stream()
                .map(RebelBaseEntity.class::cast)
                .map(e -> rebelBaseMapper.toRebelBaseDomain(e))
                .toList();
    }
}
