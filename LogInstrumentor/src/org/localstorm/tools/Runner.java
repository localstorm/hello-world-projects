package org.localstorm.tools;

import java.io.File;
import java.io.IOException;
import org.localstorm.tools.zip.ZipProcessor;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Runner 
{
    public static void main(String[] args) throws IOException
    {
        ZipProcessor zp = new ZipProcessor(new ClassFileHandler());
        zp.process(new File("input.ear"));
    }
}
