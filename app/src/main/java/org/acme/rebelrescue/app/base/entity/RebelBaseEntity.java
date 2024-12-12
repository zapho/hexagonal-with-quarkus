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
package org.acme.rebelrescue.app.base.entity;

import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

import java.util.List;

@Entity(name = "rebelbase")
@NamedQueries({
        @NamedQuery(name = "RebelBase.findByMaxRiskLevel", query = "from rebelbase rb where rb.riskLevel <= :riskLevel")
})
public class RebelBaseEntity extends PanacheEntity {
    public String name;
    public int riskLevel;
    public int shipCapacity;

    public static List<RebelBaseEntity> findByMaxRiskLevel(int maxRiskLevel) {
        return find("#RebelBase.findByMaxRiskLevel", Parameters.with("riskLevel", maxRiskLevel)).stream()
                .map(RebelBaseEntity.class::cast)
                .toList();
    }
}
