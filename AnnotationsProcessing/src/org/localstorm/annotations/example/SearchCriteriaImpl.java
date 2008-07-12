package org.localstorm.annotations.example;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SearchCriteriaImpl extends SearchCriteria
{
    @InjectionAnnotation(resourceName="fuck")
    private String myField;
    
    //@MethodAnnotation(mappedName="overridingValue") // Annotation changed!
    // Annotation from superclass had disapeared!
    @Override
    public String getField1Minimum() 
    {
        return "Hello!";
    }

    @MethodAnnotation(mappedName="fieldX")
    private String getFuckingMinimum() 
    {
        return "Hello!";
    }
}
