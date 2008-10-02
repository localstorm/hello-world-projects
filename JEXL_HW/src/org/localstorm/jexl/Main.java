/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.jexl;

import org.apache.commons.jexl.Expression;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.JexlHelper;

/**
 *
 * @author localstorm
 */
public class Main {

    public static class Another {
        private Boolean foo = true;
        
        
        public Boolean foo()
        {
            return foo;
        }
        
        public int goo()
        {
            return 100;
        }
        
    }
    
    public static class Foo {
        private Another inner;
        
        public Foo() {
            inner = new Another();
        }

        public Another getInner()
        {
            return inner;
        }
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        String jexlExp = "(foo.getInner().foo() eq true) and (foo.getInner().goo() >= 99)";
        Expression e = ExpressionFactory.createExpression( jexlExp );

        // Create a context and add data
        JexlContext jc = JexlHelper.createContext();
        jc.getVars().put("foo", new Foo() );

        // Now evaluate the expression, getting the result
        Object o = e.evaluate(jc);
        
        System.out.println("Result: "+o);
    }

}
