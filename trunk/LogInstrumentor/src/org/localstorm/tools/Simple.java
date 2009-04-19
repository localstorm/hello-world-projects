package org.localstorm.tools;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Simple 
{
    @Logged
    public void boo()
    {
        System.out.println("\tBoo!");
    }

    @Logged(100)
    public void foo(int a) throws Exception
    {
        Thread.sleep(1000);
        System.out.println("\tFoo!");
    }

    @Logged
    public void goo()
    {
        System.out.println("\tGoo!");
        throw new RuntimeException();
    }

    public static void main(String[] args) throws Exception
    {
        (new Simple()).foo(1);
        (new Simple()).foo(2);
        (new Simple()).boo();
        (new Simple()).boo();
        (new Simple()).goo();
    }
}
