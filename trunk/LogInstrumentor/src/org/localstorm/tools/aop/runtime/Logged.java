package org.localstorm.tools.aop.runtime;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author Alexey Kuznetsov
 */
@Retention(RetentionPolicy.CLASS)
public @interface Logged
{
    long value() default -1;
}
