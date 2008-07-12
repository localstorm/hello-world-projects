/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.annotations.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        SearchCriteriaImpl scm = new SearchCriteriaImpl();
        
        System.out.println("=======METHODS!=======");
        
        // NOTE: no methods from super classes
        for (Method m : scm.getClass().getDeclaredMethods() )
        {
            // m.setAccessible(true) 
            System.out.println("CHECKING: "+m.getName());
            Annotation a = m.getAnnotation(MethodAnnotation.class);

            if (a!=null)
            {
                System.out.println( "@FilteringCriteria annotated METHOD: "+ m.getName() );
                MethodAnnotation fc = (MethodAnnotation) a;
                System.out.println("METHOD mapped name: " + fc.mappedName());
            }
        }
        
        System.out.println("=======FIELDS!=======");
        
        // NOTE: no fields from super classes
        for (Field f : scm.getClass().getDeclaredFields() )
        {
            System.out.println("CHECKING: "+f.getName());
            
            Annotation a = f.getAnnotation(InjectionAnnotation.class);
            if ( a!=null )
            {
                System.out.println("@InjectionAnnotation annotated FIELD: "+f.getName());
                System.out.println("RESOURCE to be injected: "+((InjectionAnnotation)a).resourceName());
                
                System.out.println("Attempting resource injection!");
                f.setAccessible(true); // Fucking out private access control
                f.set(scm, "fuckValue");
                System.out.println("Resource injected to field!");
            }
        }
        
        
        System.out.println("=======FIELDS FROM SUPERCLASS!=======");
        // NOTE: fields from super classes!!
        for (Field f : scm.getClass().getSuperclass().getDeclaredFields() )
        {
            System.out.println("CHECKING: "+f.getName());
            
            Annotation a = f.getAnnotation(InjectionAnnotation.class);
            if ( a!=null )
            {
                System.out.println("@InjectionAnnotation annotated FIELD: "+f.getName());
                System.out.println("RESOURCE to be injected: "+((InjectionAnnotation)a).resourceName());
                
                System.out.println("Attempting resource injection for super class!");
                
                f.setAccessible(true); // Fucking out private access control
                f.set(scm, "secretResourceValue");
                System.out.println("Resource injected to superclass field!");
            }
        }
        
        
    }

}
