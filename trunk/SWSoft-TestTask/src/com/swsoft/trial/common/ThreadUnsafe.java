/**
 * Copyright 1986-2007 Alexey Kuznetsov
 *
 * All rights reserved. 
 */

package com.swsoft.trial.common;

import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.*;

/**
 * This annotation is used to mark thread-unsafe classes, methods
 * @author Kuznetsov A.
 */
@Target({TYPE, METHOD})
public @interface ThreadUnsafe {
}
