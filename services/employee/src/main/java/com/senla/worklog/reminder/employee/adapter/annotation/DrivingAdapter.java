package com.senla.worklog.reminder.employee.adapter.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Indicates that a class is a driving adapter.
 * <p>
 * A driving adapter is the one that initiate the interaction between an external system (such as a messaging system
 * or web service) and the internal domain of the application. This annotation can be used to mark a class as a driving
 * adapter, and to provide a suggested name for the Spring bean that represents the adapter.
 * </p>
 * <p>
 * This annotation is a meta-annotation that is annotated with {@link Component}, which means that it can be automatically
 * detected and registered as a Spring bean if component scanning is enabled.
 * </p>
 * <p>
 * The suggested name for the Spring bean can be specified using the {@link #value} attribute, which is an alias for the
 * {@code value} attribute of the {@link Component} annotation.
 * </p>
 * Example usage:
 * <pre>
 * {@code @DrivingAdapter("myAdapter")}
 * {@code public class MyAdapter {
 *       // ...
 *   }
 * }</pre>
 */
@Documented
@Component
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DrivingAdapter {

    /**
     * The suggested name for the Spring bean that represents the adapter.
     *
     * @return the suggested name, if any, or an empty string if no name is suggested.
     * @see Component#value
     */
    @AliasFor(annotation = Component.class)
    String value() default "";
}
