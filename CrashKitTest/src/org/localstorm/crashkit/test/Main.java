package org.localstorm.crashkit.test;

import com.yoursway.crashkit.CrashKit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author localstorm
 */
public class Main {

    public static final CrashKit crashKit = CrashKit.connectApplication(
            "Just For Fun", "1.0.0", "localstorm", "justForFun",
        new String[] {"com.example.myproduct", "org.localstorm.crashkit.test"});
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        for (int i=0; i<1000; i++)
        {
            try {
                System.out.println("["+i+"]");
                // run event loop, process request, whatever

                throw new IllegalArgumentException("Блять! -"+i);
            } catch (Throwable e) {
                //CrashKit.bug(e);
                CrashKit.major(e);
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException ex)
                {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //throw new NullPointerException("Fuck off!");
    }

}
