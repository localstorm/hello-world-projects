package org.localstorm.tools.aop.weaver.zip;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public interface FileHandler
{
    public void handle(File f) throws IOException;
}
