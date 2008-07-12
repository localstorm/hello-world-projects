package org.localstorm.annotations.example;

/**
 *
 * @author Alexey Kuznetsov
 */
public abstract class SearchCriteria 
{
    @InjectionAnnotation(resourceName="secretResource")
    private String secretSuperField;
    
    // Disappeared or overrided in SearchCriteriaImpl
    @MethodAnnotation(mappedName="toBeOverrided")
    public String getField1Minimum() {
        return "Hi";
    }
    
    // This annotation is inherited, but this method is not checked 
    // by getDeclaredMethods. getMethods or accessing superclass 
    // should be used.
    @MethodAnnotation(mappedName="toBeInherited")
    public String getField2Minimum() {
        return "Hello";
    }
    
}
