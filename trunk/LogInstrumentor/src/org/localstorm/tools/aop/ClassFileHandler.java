package org.localstorm.tools.aop;

import org.localstorm.tools.aop.zip.FileHandler;
import java.io.File;
import java.io.IOException;
import javassist.ClassPool;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ClassFileHandler implements FileHandler
{
    private final ClassPool pool;

    public ClassFileHandler(ClassPool pool)
    {
        this.pool = pool;
    }

    public void handle(File f)
            throws IOException
    {
        try {

            if (f.getName().toLowerCase().endsWith(".class"))
            {
                System.out.println("Trying to instrument class-file: "+f.getName());
                LogInstrumentor.tryInstrument(pool, f);
                
            }
        } catch(Exception e) {
            throw new IOException(e);
        }
        
    }
}
