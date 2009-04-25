package org.localstorm.tools.aop;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.io.FileUtils;
import org.localstorm.tools.aop.zip.FileHandler;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ClassFileCopierHandler implements FileHandler
{
    private final File dir;
    private final ClassPool pool;

    public ClassFileCopierHandler(ClassPool pool, File dir)
    {
        this.dir = dir;
        this.pool = pool;
    }

    public void handle(File f)
            throws IOException
    {
            if (f.getName().toLowerCase().endsWith(".class"))
            {
                FileInputStream fis = null;
                String className = null;

                try{
                    fis = new FileInputStream(f);
                    CtClass cl = this.pool.makeClassIfNew(fis);
                    className  = cl.getName();
                } finally {
                    if (fis!=null) {
                        fis.close();
                    }
                }

                String file = className.replaceAll("\\.", "/");
                String abs = this.dir.getAbsolutePath()+"/"+file+".class";

                FileUtils.copyFile(f, new File(abs));
            }
    }

}
