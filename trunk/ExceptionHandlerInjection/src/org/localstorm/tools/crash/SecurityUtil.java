package org.localstorm.tools.crash;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SecurityUtil 
{
    public static boolean checkAccess(ThreadGroup group)
    {
        try {
            group.checkAccess();
            return true;
        }catch(SecurityException e){
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkAccess(Thread thread)
    {
        try {
            thread.checkAccess();
            return true;
        }catch(SecurityException e){
            e.printStackTrace();
            return false;
        }
    }

}
