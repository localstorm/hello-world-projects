package org.localstorm.tools.aop.weaver.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Alexey Kuznetsov
 */
class DirLister 
{

    public static List<File> getListing( File aStartingDir ) throws FileNotFoundException {

        List<File> result = new ArrayList<File>();
        File[] filesAndDirs = aStartingDir.listFiles();
        List<File> filesDirs = Arrays.asList(filesAndDirs);

        for(File file : filesDirs) {

            if ( !file.isDirectory() ) {
                result.add(file); //always add, even if directory
            } else {
                //must be a directory
                //recursive call!
                List<File> deeperList = getListing(file);
                result.addAll(deeperList);
            }
        }

        return result;
    }

}
