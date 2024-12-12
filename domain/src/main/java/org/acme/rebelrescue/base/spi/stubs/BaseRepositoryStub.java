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
package org.acme.rebelrescue.base.spi.stubs;

import org.acme.ddd.Stub;
import org.acme.rebelrescue.base.RebelBase;
import org.acme.rebelrescue.base.RiskLevel;
import org.acme.rebelrescue.base.spi.BaseRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Stub
public class BaseRepositoryStub implements BaseRepository {
    private static final Logger LOG = LogManager.getLogger(BaseRepositoryStub.class);

    private final static List<RebelBase> DEFAULT_BASES = List.of(
            new RebelBase("Tatooine", RiskLevel.SECURE, 10),
            new RebelBase("Dagobah", RiskLevel.COMPROMISED, 2),
            new RebelBase("Hoth", RiskLevel.CRITICAL, 100),
            new RebelBase("Endor", RiskLevel.ELEVATED, 5),
            new RebelBase("Coruscant", RiskLevel.CAUTIOUS, 20),
            new RebelBase("Naboo", RiskLevel.SECURE, 3)
    );

    private final List<RebelBase> bases;

    public BaseRepositoryStub() {
        this(DEFAULT_BASES);
    }

    public BaseRepositoryStub(List<RebelBase> bases) {
        LOG.info("Using stub base repository");
        this.bases = bases;
    }

    @Override
    public List<RebelBase> findBaseByMaxRiskLevel(int riskLevel) {
        return bases.stream()
                .filter(b -> b.riskLevel().getLevel() < riskLevel)
                .toList();
    }
}
