package org.localstorm.threads;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;

/**
 * @author localstorm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception
    {
        ThreadRegistrator reg = new ThreadRegistrator();

        ExecutorService es = Executors.newFixedThreadPool(5);

        for (int i=0; i<10; i++)
        {
            ManagedRunnable mr = new ManagedRunnable(new Runnable(){
                public void run()
                {
                    for (int i=0;i<1000;i++) {
                        try
                        {
                            Thread.sleep(1000);
                        }catch(InterruptedException e){
                            System.out.println("Goo!!! "+i);
                        }
                        System.out.println("Goo "+i);
                    }
                }
            }, ""+i, reg);

            es.submit(mr, "Done");
        }

        Thread.sleep(5000);

        //System.out.println("Killing!");
        //forceKill(mr);

        //Thread.sleep(5000);

        es.shutdownNow();

        Thread.sleep(5000);
        reg.killAll();

        //System.out.println("Result: " + f.get());
    }

    private static class ManagedRunnable implements Runnable
    {
        private Runnable runnable;
        private String id;
        private ThreadRegistrator reg;

        public ManagedRunnable(Runnable c, String id, ThreadRegistrator reg)
        {
            this.runnable = c;
            this.id       = id;
            this.reg      = reg;
        }

        public void run()
        {
            Thread thread = Thread.currentThread();
            this.reg.register(this.id, thread);

            try
            {
                this.runnable.run();
            } finally {
                this.reg.unregister(this.id);
            }
        }
    }

}
