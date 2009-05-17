package org.localstorm.tools.crash;

import static java.lang.Thread.*;
/**
 *
 * @author Alexey Kuznetsov
 */
public class InjectFirstExceptionHandler implements ThreadCommand
{
    private ExceptionHandlerPrototype ehp;

    public InjectFirstExceptionHandler(ExceptionHandlerPrototype ehp) {
        this.ehp = ehp;
    }

    public void handle(Thread t) {

        if (SecurityUtil.checkAccess(t)) {
            UncaughtExceptionHandler ueh = ehp.getByPrototype();
            UncaughtExceptionHandler old = t.getUncaughtExceptionHandler();

            UncaughtExceptionHandler toSet;

            if (old==null) {
                toSet = ueh;
            } else {
                ChainingExceptionHandler ceh = new ChainingExceptionHandler();
                ceh.invokeBefore(ueh);
                ceh.invokeAfter(old);
                toSet = ceh;
            }

            t.setUncaughtExceptionHandler(toSet);
        } 
    }

}
