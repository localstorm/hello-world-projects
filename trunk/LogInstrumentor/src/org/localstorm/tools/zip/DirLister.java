package org.localstorm.tools.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Alexey Kuznetsov
 */
public class DirLister 
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

    /**
    * Directory is valid if it exists, does not represent a file, and can be read.
    */
    private static void validateDirectory ( File aDirectory ) throws FileNotFoundException {

        if (aDirectory == null) {
            throw new IllegalArgumentException("Directory should not be null.");
        }

        if (!aDirectory.exists()) {
            throw new FileNotFoundException("Directory does not exist: " + aDirectory);
        }

        if (!aDirectory.isDirectory()) {
            throw new IllegalArgumentException("Is not a directory: " + aDirectory);
        }

        if (!aDirectory.canRead()) {
            throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
        }
    }

}
