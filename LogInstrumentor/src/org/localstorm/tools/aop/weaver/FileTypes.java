package org.localstorm.tools.aop.weaver;

import java.io.File;

/**
 *
 * @author Alexey Kuznetsov
 */
public class FileTypes 
{
    public final static String BYTECODE_FILE_EXT = ".class";

    public static boolean isBytecodeFile(File f)
    {
        return f.getName().toLowerCase().endsWith(BYTECODE_FILE_EXT);
    }
}
