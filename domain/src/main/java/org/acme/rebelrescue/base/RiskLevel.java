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

public enum RiskLevel {
    SECURE(0),        // Minimal risk of discovery
    CAUTIOUS(5),      // Low risk but requires monitoring
    ELEVATED(50),      // Moderate risk with increased empire activity
    CRITICAL(500),      // High risk of imminent discovery
    COMPROMISED(5000)    // Base location known to the empire
    ;

    private final int level;

    RiskLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}