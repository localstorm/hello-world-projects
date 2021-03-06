package org.localstorm.tools.aop.weaver;

import java.io.File;
import javassist.ClassPool;
import org.localstorm.tools.aop.weaver.zip.ZipProcessor;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Runner 
{
    private static final String DEFAULT_TMP_DIR     = ".";
    private static final String DONT_INSTRUMENT_NEXT_ASSEMBLY_KEY = "-s";
    private static final String TMP_DIR_PROPERTY    = "java.io.tmpdir";
    private static final int    DIR_NAME_LENGTH     = 10;

    public static void main(String[] args) throws Exception
    {
        if (args.length==0) {
            System.err.println("Nothing to do! Specify assembly files, please...");
            return;
        }

        ClassPool pool = ClassPool.getDefault();

        String    tmpDir = System.getProperty(TMP_DIR_PROPERTY, DEFAULT_TMP_DIR);
        File tempFileDir = new File(tmpDir);
        
        File dir = new File(RandomUtil.generateDirectoryName(tmpDir, DIR_NAME_LENGTH));
        
        ZipProcessor zp = new ZipProcessor(tempFileDir, new ClassFileCopierHandler(pool, dir), true);
        for (String file : args) {
            if (!file.equals(DONT_INSTRUMENT_NEXT_ASSEMBLY_KEY)) {
                System.out.println("Appending in classpath: "+file);
                zp.process(new File(file));
            }
        }

        System.out.println("Classpath: "+dir.getAbsolutePath());
        pool.appendClassPath(dir.getAbsolutePath());
        System.out.println("Starting processing...");

        // Prepare classpath here!
        zp = new ZipProcessor(tempFileDir, new ClassFileHandler(pool), false);

        for (int i=0; i<args.length; i++) {
            String file = args[i];
            
            if (file.equals(DONT_INSTRUMENT_NEXT_ASSEMBLY_KEY)) {
                i++;
                continue;
            }

            System.out.println("Processing: "+file);
            zp.process(new File(file));
        }
    }
}
