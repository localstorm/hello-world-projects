package org.localstrorm.tests;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: localstorm
 * Date: Nov 29, 2009
 * Time: 11:34:44 AM
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Experiment {
    String base() default "";
    String name() default "Untitled";
}
