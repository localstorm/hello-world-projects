package org.localstorm.mcc.ejb;


import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.Context;


public class ContextLookup<T>
{
    /*private static final Properties properties;
    static {
        properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, 
                "org.apache.openejb.client.LocalInitialContextFactory");
    }*/
    
    @SuppressWarnings("unchecked")
    public static <T> T lookup(Class<T> cl, String beanName)  throws RuntimeException
    {
        try {
            InitialContext ic = new InitialContext( /*properties*/ );
            Object o = ic.lookup(beanName);
            ic.close();
            return (T) o;
        } catch(NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T nativeLookup(Class<T> cl, String jndi)  throws RuntimeException
    {
        try {
            InitialContext ic = new InitialContext();
            return (T)ic.lookup(jndi);
        } catch(NamingException e) {
            throw new RuntimeException(e);
        }
    }

}

