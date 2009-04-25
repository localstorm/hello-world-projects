package org.localstorm.tools.aop.zip;

import java.io.File;

/**
 *
 * @author Alexey Kuznetsov
 */
public class Util 
{
 public static boolean deleteDirectory(File path) {
    if( path.exists() ) {
      File[] files = path.listFiles();
      for(int i=0; i<files.length; i++) {
         if(files[i].isDirectory()) {
           deleteDirectory(files[i]);
         }
         else {
           files[i].delete();
         }
      }
    }
    return( path.delete() );
  }

}
