package org.localstorm.tools.zip;

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
