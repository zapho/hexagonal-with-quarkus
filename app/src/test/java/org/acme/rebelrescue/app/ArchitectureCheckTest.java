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
package org.acme.rebelrescue.app;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;


public class ArchitectureCheckTest {
    @Test
    public void fleet_domain_should_remain_isolated_from_infrastructure() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("org.acme");

        ArchRule rule = classes()
                .that().resideInAPackage("org.acme.rebelrescue.fleet..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "org.acme.rebelrescue.fleet..",
                        "org.acme.ddd..",
                        "java..",
                        "org.apache.logging.log4j..");

        rule.check(importedClasses);
    }

    @Test
    public void ddd_should_depend_on_java_or_jakarta_enterprise_cdi_api() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("org.acme");

        ArchRule rule = classes()
                .that().resideInAPackage("org.acme.ddd..")
                .should().onlyDependOnClassesThat()
                .resideInAnyPackage(
                        "org.acme.ddd..",
                        "jakarta.enterprise.context..",
                        "jakarta.enterprise.inject..",
                        "java..");

        rule.check(importedClasses);
    }
}
