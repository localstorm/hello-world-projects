package org.localstorm.tools.aop;

import java.io.File;
import javassist.ClassPool;
import org.localstorm.tools.aop.random.RandomUtil;
import org.localstorm.tools.aop.zip.ZipProcessor;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Runner 
{
    public static final String DEFAULT_TMP_DIR = ".";
    public static final String SKIP_MEXT_PARAMETER_KEY = "-s";
    public static final String TMP_DIR_PROPERTY = "java.io.tmpdir";

    public static void main(String[] args) throws Exception
    {
        if (args.length==0) {
            System.err.println("Nothing to do! Specify assembly files, please...");
            return;
        }

        ClassPool pool = ClassPool.getDefault();

        String  tmpDir = System.getProperty(TMP_DIR_PROPERTY, DEFAULT_TMP_DIR);
        
        File dir = new File(RandomUtil.generateDirectoryName(tmpDir, 10));
        
        ZipProcessor zp = new ZipProcessor(new ClassFileCopierHandler(pool, dir), true);
        for (String file : args) {
            if (!file.equals(SKIP_MEXT_PARAMETER_KEY))
            {
                System.out.println("Appending in classpath: "+file);
                zp.process(new File(file));
            }
        }

        System.out.println("Classpath: "+dir.getAbsolutePath());
        pool.appendClassPath(dir.getAbsolutePath());
        System.out.println("Starting processing...");

        // Prepare classpath here!
        zp = new ZipProcessor(new ClassFileHandler(pool), false);

        for (int i=0; i<args.length; i++) {
            String file = args[i];
            if (file.equals(SKIP_MEXT_PARAMETER_KEY))
            {
                i++;
                continue;
            }

            System.out.println("Processing: "+file);
            zp.process(new File(file));
        }
    }
}
