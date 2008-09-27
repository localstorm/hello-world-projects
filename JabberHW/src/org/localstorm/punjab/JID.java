package org.localstorm.punjab;

/**
 *
 * @author Alexey Kuznetsov
 */
public class JID 
{
    private String name;
    private String service;
    
    public JID(String jid) {
        String[] array = jid.split("@");
        if (array.length!=2)
        {
            throw new IllegalArgumentException("Invalid JID:"+jid);
        }
         
        this.name    = array[0];
        this.service = array[1];
    }

    public String getName() {
        return name;
    }

    public String getService() {
        return service;
    }

    @Override
    public String toString() {
        return name+"@"+service;
    }
    
}
