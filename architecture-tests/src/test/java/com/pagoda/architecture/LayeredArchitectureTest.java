package com.pagoda.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class LayeredArchitectureTest {

    @Test
    void enforceDDDLayering() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.pagoda");

        Architectures.layeredArchitecture().consideringAllDependencies()
                .layer("Core").definedBy("com.pagoda.core..")
                .layer("Application").definedBy("com.pagoda.application..")
                .layer("Infrastructure").definedBy("com.pagoda.infrastructure..")
                .layer("Shared").definedBy("com.pagoda.shared..")
                .layer("API").definedBy("com.pagoda.api..")

                .whereLayer("Core").mayOnlyBeAccessedByLayers("Application", "Infrastructure", "Shared", "API")
                .whereLayer("Application").mayOnlyBeAccessedByLayers("Infrastructure", "API")
                .whereLayer("Infrastructure").mayNotBeAccessedByAnyLayer()
                .whereLayer("Shared").mayOnlyBeAccessedByLayers("API")
                .whereLayer("API").mayNotBeAccessedByAnyLayer()

                .check(classes);
    }

    @Test
    void testFixtures_should_not_be_used_in_main_code() {
        JavaClasses mainClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("com.pagoda");

        noClasses()
                .that().resideOutsideOfPackage("..testfixtures..")
                .should().dependOnClassesThat().resideInAnyPackage("..testfixtures..")
                .because("Test fixture classes must not be used in production code")
                .check(mainClasses);
    }
}