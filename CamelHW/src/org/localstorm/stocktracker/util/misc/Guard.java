package org.localstorm.stocktracker.util.misc;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;

/**
 * Simple checks for your methods
 * You can use static import to use it like asserts.
 *
 * @author Alexey Kuznetsov
 */
public class Guard
{
    @SuppressWarnings("unchecked")
    public static <T extends Exception> void checkNotNull(Object obj,
                                                          Class<T> exceptionClass,
                                                          String errorMessageTemplate,
                                                          Object[] params)
        throws T
    {
        if (obj == null)
        {
            if (errorMessageTemplate == null || params == null || params.length == 0)
            {
                Object o = null;
                try
                {
                    o = exceptionClass.newInstance();
                } catch (Exception e)
                {
                    throw new IllegalArgumentException(e.toString());
                }

                throw (T) o;
            } else
            {
                Object o = null;

                try
                {
                    Constructor c = exceptionClass.getConstructor(new Class[] { String.class });
                    o = c.newInstance(new Object[] { MessageFormat.format(errorMessageTemplate, params) });
                } catch (Exception e)
                {
                    throw new IllegalArgumentException(e.toString());
                }

                throw (T) o;
            }
        }
    }

    public static <T extends Exception> void checkNotNull(Object obj,
                                        Class<T> exceptionClass,
                                        String errorMessage)
        throws T
    {
        Guard.checkNotNull(obj, exceptionClass, errorMessage, Guard.EMPTY_PARAMS);
    }

    
    public static Object[] prepareSingleParameter(Object o)
    {
        return new Object[] { o };
    }

    private final static Object[] EMPTY_PARAMS = new Object[] { "" };
}
