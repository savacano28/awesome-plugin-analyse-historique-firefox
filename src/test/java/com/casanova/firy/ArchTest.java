package com.casanova.firy;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.casanova.firy");

        noClasses()
            .that()
            .resideInAnyPackage("com.casanova.firy.service..")
            .or()
            .resideInAnyPackage("com.casanova.firy.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.casanova.firy.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
