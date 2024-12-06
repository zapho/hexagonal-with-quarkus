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
package org.acme;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


public class ArchitectureCheckTest {
    @Test
    public void domain_should_not_rely() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("org.acme");

        ArchRule rule = classes()
                .that().resideInAPackage("..domain..")
                .should().onlyAccessClassesThat()
                .resideInAnyPackage("..domain..", "java..");

        rule.check(importedClasses);
    }
}
