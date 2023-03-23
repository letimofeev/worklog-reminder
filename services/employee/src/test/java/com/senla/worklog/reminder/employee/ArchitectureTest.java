package com.senla.worklog.reminder.employee;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

/**
 * This class defines a set of architectural rules that are applied to the classes within the specified package(s) during testing
 * <p>
 * The `AnalyzeClasses` annotation is used to specify the package(s) to be analyzed by the architectural rules. In this case,
 * the package being analyzed is `com.senla.worklog.reminder.employee`
 * <p>
 * The architectural rules defined within this class are used to ensure that the classes within the specified package(s) adhere
 * to a set of established architectural principles, such as the hexagonal architecture pattern, dependency injection best
 * practices, and logging framework usage
 */
@SuppressWarnings("unused")
@AnalyzeClasses(packages = {"com.senla.worklog.reminder.employee"})
public class ArchitectureTest {

    /**
     * This rule verifies that no class in the application uses the `java.util.logging` framework for logging,
     * as it is considered outdated and not as flexible as other modern logging frameworks
     */
    @ArchTest
    public static final ArchRule JAVA_UTIL_LOGGING_IS_FORBIDDEN = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

    /**
     * This rule verifies that no class in the application uses field injection, as this can make it difficult to
     * reason about the dependencies of a class and can lead to issues with testing and maintainability
     */
    @ArchTest
    public static final ArchRule FIELD_INJECTION_SHOULD_NOT_BE_USED = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

    /**
     * This rule verifies that no class in the application directly accesses the standard output or error streams, as doing so
     * can cause issues with logging and interfere with the ability to capture and redirect output in production environments.
     */
    @ArchTest
    public static final ArchRule STANDARD_OUTPUT_STREAMS_SHOULD_NOT_BE_USED = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

    /**
     * This rule enforces a hexagonal architecture for the application, ensuring that the code is organized into layers
     * that are cleanly separated and independently testable. The rule verifies that the domain layer does not depend on
     * any other layer, and that adapters depend only on the domain layer
     * <p>
     * The rule specifies the packages that should contain each layer of the architecture, as follows:
     * - Domain models should be located in packages under "..domain.model..".
     * - Domain ports and services should be located in packages under "..domain.port.." and "..domain.service..".
     * - Application services should be located in packages under "..application..".
     * - Inbound (Driving) adapters (f.e. REST endpoints) should be located in packages under "..adapter.in..".
     * - Outbound (Driven) adapters (f.e. persistence with JPA) should be located in packages under "..adapter.out..".
     * <p>
     * Any violations of the hexagonal architecture will result in test failures.
     */
    @ArchTest
    public static final ArchRule HEXAGONAL_ARCHITECTURE = onionArchitecture()
            .domainModels("..domain.model..")
            .domainServices("..domain.port..", "..domain.service..")
            .applicationServices("..application..")
            .adapter("restIn", "..adapter.in.rest..")
            .adapter("restOut", "..adapter.out.rest..")
            .adapter("jpaOut", "..adapter.out.jpa..");
}
