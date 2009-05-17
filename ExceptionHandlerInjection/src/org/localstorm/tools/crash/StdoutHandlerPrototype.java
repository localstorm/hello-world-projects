package org.localstorm.tools.crash;

/**
 *
 * @author Alexey Kuznetsov
 */
public class StdoutHandlerPrototype extends ExceptionHandlerPrototype
{

    public StdoutHandlerPrototype()
    {
    }

    @Override
    public void uncaughtException(Thread t, Throwable e)
    {
        System.out.print("Uncool: ");
        e.printStackTrace(System.out);
        System.out.flush();
    }

    @Override
    public void cleanup()
    {
        // Nothing to do
    }





}
