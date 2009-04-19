package org.localstorm.tools;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Simple 
{
    @Log
    public void foo() throws Exception
    {
        //ThreadLocal tl = new ThreadLocal();
        //tl.remove()
        Thread.sleep(1000);
        System.out.println("\tFoo!");
//        throw new RuntimeException();
    }

    @Log
    public void goo()
    {
        //ThreadLocal tl = new ThreadLocal();
        //tl.remove()
        
        System.out.println("\tGoo!");
        throw new RuntimeException();
    }

    public static void main(String[] args) throws Exception
    {
        (new Simple()).foo();
        (new Simple()).foo();
        (new Simple()).goo();
    }
}
