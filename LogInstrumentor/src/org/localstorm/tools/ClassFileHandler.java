package org.localstorm.tools;

import org.localstorm.tools.zip.*;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ClassFileHandler implements FileHandler
{

    public ClassFileHandler()
    {
    }

    public void handle(File f)
            throws IOException
    {
        try {

            if (f.getName().toLowerCase().endsWith(".class"))
            {
                System.out.println("Trying to instrument class-file: "+f.getName());
                if (LogInstrumentor.instrument(f))
                {
                    System.out.println(f.getName()+" instrumented!");
                } else {
                    System.out.println("**"+f.getName()+" not instrumented!**");
                }
            }
        } catch(Exception e) {
            throw new IOException(e);
        }
        
    }
}
