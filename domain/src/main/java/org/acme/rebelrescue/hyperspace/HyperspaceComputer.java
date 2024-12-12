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
package org.acme.rebelrescue.hyperspace;

import org.acme.ddd.DomainService;
import org.acme.rebelrescue.hyperspace.api.ComputeTravelDuration;

import java.time.Duration;

@DomainService
public class HyperspaceComputer implements ComputeTravelDuration {
    @Override
    public Duration to(String baseName) {
        // calculate the distance between the initial of the base name and the letter 'a'
        int distance = baseName.charAt(0) - 'a';
        return Duration.ofHours(distance);
    }
}
