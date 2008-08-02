/*
 * $Id: EJBInterceptor.java 4 2008-07-21 13:48:25Z samaxes $
 *
 * Copyright 2008 samaxes.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.localstorm.mcc.web.interceptors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.controller.ExecutionContext;
import net.sourceforge.stripes.controller.Interceptor;
import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;
import net.sourceforge.stripes.exception.StripesRuntimeException;
import net.sourceforge.stripes.util.ReflectUtil;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.web.annotations.EJBBean;

/**
 * <p>
 * An {@link Interceptor} that uses a initial context to inject EJB beans into newly created ActionBeans immediateley
 * following ActionBeanResolution.
 * </p>
 * 
 * <p>
 * To configure the EJBInterceptor for use you will need to add the following to your web.xml (assuming no other
 * interceptors are yet configured):
 * </p>
 * 
 * <pre>
 * &lt;init-param&gt;
 *     &lt;param-name&gt;Interceptor.Classes&lt;/param-name&gt;
 *     &lt;param-value&gt;
 *         com.samaxes.stripes.integration.ejb.EJBInterceptor,
 *         net.sourceforge.stripes.controller.BeforeAfterMethodInterceptor
 *     &lt;/param-value&gt;
 * &lt;/init-param&gt;
 * </pre>
 * 
 * <p>
 * If one or more interceptors are already configured in your web.xml simply separate the fully qualified names of the
 * interceptors with commas (additional whitespace is ok).
 * </p>
 * 
 * @see EJBBean
 * @author Samuel Santos
 * @version $Revision: 4 $
 */
@Intercepts(LifecycleStage.ActionBeanResolution)
public class EJBInterceptor implements Interceptor {

    /** Lazily filled in map of Class to methods annotated with EJBBean. */
    private static Map<Class<?>, Collection<Method>> methodMap = new ConcurrentHashMap<Class<?>, Collection<Method>>();

    /** Lazily filled in map of Class to fields annotated with EJBBean. */
    private static Map<Class<?>, Collection<Field>> fieldMap = new ConcurrentHashMap<Class<?>, Collection<Field>>();

    /**
     * Allows ActionBean resolution to proceed and then once the ActionBean has been located performs the EJB injection.
     * 
     * @param ctx the current execution context
     * @return the Resolution produced by calling context.proceed()
     * @throws Exception if the EJB binding process produced unrecoverable errors
     */
    @Override
    public Resolution intercept(ExecutionContext ctx) throws Exception {
        Resolution resolution = ctx.proceed();
        ActionBean bean = ctx.getActionBean();

        // First inject any values using annotated methods
        for (Method method : getMethods(bean.getClass())) {
            try {
                EJBBean ejbBean = method.getAnnotation(EJBBean.class);
                String name = ejbBean.value();
                boolean nameSupplied = (name!=null && name.length()>0);
                
                if (!nameSupplied) {
                    name = methodToPropertyName(method);
                }
                
                Object managedBean = findEJB(name);
                method.invoke(bean, managedBean);
            } catch (Exception e) {
                throw new StripesRuntimeException("Exception while trying to lookup and inject "
                        + "an EJB bean into a bean of type " + bean.getClass().getSimpleName() + " using method "
                        + method.toString(), e);
            }
        }

        // And then inject any properties that are annotated
        for (Field field : getFields(bean.getClass())) {
            try {
                EJBBean ejbBean = field.getAnnotation(EJBBean.class);
                String name = ejbBean.value();
                boolean nameSupplied = (name!=null && name.length()>0);
                 
                if (!nameSupplied) {
                    name = field.getName();
                }
                
                Object managedBean = findEJB(name);
                field.set(bean, managedBean);
            } catch (Exception e) {
                throw new StripesRuntimeException("Exception while trying to lookup and inject "
                        + "a EJB bean into a bean of type " + bean.getClass().getSimpleName()
                        + " using field access on field " + field.toString(), e);
            }
        }

        return resolution;
    }

    /**
     * Fetches the methods on a class that are annotated with EJBBean. The first time it is called for a particular
     * class it will introspect the class and cache the results. All non-overridden methods are examined, including
     * protected and private methods. If a method is not public an attempt it made to make it accessible - if it fails
     * it is removed from the collection and an error is logged.
     * 
     * @param clazz the class on which to look for EJBBean annotated methods
     * @return the collection of methods with the annotation
     */
    protected static Collection<Method> getMethods(Class<?> clazz) {
        Collection<Method> methods = methodMap.get(clazz);
        if (methods == null) {
            methods = ReflectUtil.getMethods(clazz);
            Iterator<Method> iterator = methods.iterator();

            while (iterator.hasNext()) {
                Method method = iterator.next();
                if (!method.isAnnotationPresent(EJBBean.class)) {
                    iterator.remove();
                } else {
                    // If the method isn't public, try to make it accessible
                    if (!method.isAccessible()) {
                        try {
                            method.setAccessible(true);
                        } catch (SecurityException se) {
                            throw new StripesRuntimeException("Method " + clazz.getName() + "." + method.getName()
                                    + "is marked " + "with @EJBBean and is not public. An attempt to call "
                                    + "setAccessible(true) resulted in a SecurityException. Please "
                                    + "either make the method public or modify your JVM security "
                                    + "policy to allow Stripes to setAccessible(true).", se);
                        }
                    }

                    // Ensure the method has only the one parameter
                    if (method.getParameterTypes().length != 1) {
                        throw new StripesRuntimeException(
                                "A method marked with @EJBBean must have exactly one parameter: "
                                        + "the bean to be injected. Method [" + method.toGenericString() + "] has "
                                        + method.getParameterTypes().length + " parameters.");
                    }
                }
            }

            methodMap.put(clazz, methods);
        }

        return methods;
    }

    /**
     * Fetches the fields on a class that are annotated with EJBBean. The first time it is called for a particular class
     * it will introspect the class and cache the results. All non-overridden fields are examined, including protected
     * and private fields. If a field is not public an attempt it made to make it accessible - if it fails it is removed
     * from the collection and an error is logged.
     * 
     * @param clazz the class on which to look for EJBBean annotated fields
     * @return the collection of methods with the annotation
     */
    protected static Collection<Field> getFields(Class<?> clazz) {
        Collection<Field> fields = fieldMap.get(clazz);
        if (fields == null) {
            fields = ReflectUtil.getFields(clazz);
            Iterator<Field> iterator = fields.iterator();

            while (iterator.hasNext()) {
                Field field = iterator.next();
                if (!field.isAnnotationPresent(EJBBean.class)) {
                    iterator.remove();
                } else if (!field.isAccessible()) {
                    // If the field isn't public, try to make it accessible
                    try {
                        field.setAccessible(true);
                    } catch (SecurityException se) {
                        throw new StripesRuntimeException("Field " + clazz.getName() + "." + field.getName()
                                + "is marked " + "with @EJBBean and is not public. An attempt to call "
                                + "setAccessible(true) resulted in a SecurityException. Please "
                                + "either make the field public, annotate a public setter instead "
                                + "or modify your JVM security policy to allow Stripes to " + "setAccessible(true).",
                                se);
                    }
                }
            }

            fieldMap.put(clazz, fields);
        }

        return fields;
    }

    /**
     * Looks up a EJB managed bean from an Initial Context.
     * 
     * @param name the name of the EJB bean to look for
     * @exception StripesRuntimeException StripesRuntimeException is thrown if it is not possible to find a matching
     *            bean in the initial context.
     */
    protected static Object findEJB(String name) {
        // Try to lookup using the name provided
        try {
            return ContextLookup.lookup(Object.class, name);
        } catch (RuntimeException e) {
            throw new StripesRuntimeException("Unable to find an EJBBean with name [" + name
                    + "] in the initial context.");
        }
    }

    /**
     * A slightly unusual, and somewhat "loose" conversion of a method name to a property name. Assumes that the name is
     * in fact a mutator for a property and will do the usual {@code setFoo} to {@code foo} conversion if the method
     * follows the normal syntax, otherwise will just return the method name.
     * 
     * @param m the method to determine the property name of
     * @return a String property name
     */
    protected static String methodToPropertyName(Method m) {
        String name = m.getName();
        if (name.startsWith("set") && name.length() > 3) {
            String ret = name.substring(3, 4).toLowerCase();
            if (name.length() > 4) {
                ret += name.substring(4);
            }
            return ret;
        } else {
            return name;
        }
    }
}
