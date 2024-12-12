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
import jakarta.transaction.Transactional;
import org.acme.rebelrescue.base.RebelBase;

import java.util.List;

@ApplicationScoped
public class RebelBaseManager {

    JpaBaseRepository jpaBaseRepository;

    public RebelBaseManager(JpaBaseRepository jpaBaseRepository) {
        this.jpaBaseRepository = jpaBaseRepository;
    }

    @Transactional
    public void createRebelBase(RebelBase rebelBase) {
        jpaBaseRepository.create(rebelBase);
    }

    @Transactional
    public List<RebelBase> getAllRebelBases() {
        return jpaBaseRepository.findAll();
    }
}
