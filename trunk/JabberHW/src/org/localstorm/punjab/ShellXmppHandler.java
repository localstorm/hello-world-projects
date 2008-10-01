package org.localstorm.punjab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ShellXmppHandler implements XmppHandler
{

    public String getDenialMessage()
    {
        return "You're not authorized for this service!";
    }

    public boolean isAllowed(String from)
    {
        return true;
    }

    public String onMessage(String msg, String from)
    {
        System.out.println("Message from "+from+": "+msg);
        ProcessBuilder pb = new ProcessBuilder("bash", "-c" , msg);
        pb.redirectErrorStream(true);
        
        InputStream is = null;
        try {
        
            final Process p = pb.start();
            
            Thread t = new Thread(){

                @Override
                public void run()
                {
                    try {
                        Thread.sleep(5000);
                    } catch(InterruptedException e ) {
                        
                    }
                    p.destroy();
                }
                
            };
            
            t.start();
            
            is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int i = 1000;
            
            StringBuilder sb = new StringBuilder();
            String s;
            while (i>0 && (s = br.readLine())!=null)
            {
                sb.append(s);
                sb.append("\n");
                i--;
            }
            
            if (i==0)
            {
                sb.append("... Truncated ...\n");
            }

            
            
            return sb.toString();
            
        } catch(Exception e) {
        
            e.printStackTrace();
            return "ERROR: "+e.getMessage();
        
        } finally {
            if (is!=null)
            {
                try {
                    is.close();
                } catch(IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        
    
    }
    
}
