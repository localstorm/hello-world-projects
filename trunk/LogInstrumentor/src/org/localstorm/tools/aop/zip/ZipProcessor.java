package org.localstorm.tools.aop.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.localstorm.tools.aop.random.RandomUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ZipProcessor 
{
    private static final int RANDOM_FILE_NAME_LENGTH = 10;
  
    private static final String[] zipFileExtensions = new String[]{".zip", ".ear", ".war", ".jar"};

    private FileHandler handler;
    private boolean readOnly;

    public ZipProcessor(FileHandler fh, boolean readOnly)
    {
        this.handler = fh;
        this.readOnly = readOnly;
    }

    public void process(File assembly) throws IOException
    {
        File tempDir = getNewDir();

        if (!tempDir.mkdirs())
        {
            throw new IOException("Can't create directory: ["+tempDir.getName()+"]");
        }
        
        this.unzipAssembly(tempDir, assembly);

        if (!readOnly) {
            this.zipAssembly(tempDir, assembly);
        }

        if (!Util.deleteDirectory(tempDir))
        {
            System.err.println("Directory ["+tempDir.getName()+"] was not deleted");
        }
    }

    private ZipEntry genZipEntry(File tempDir, File toZip)
    {
        String toZipAp = toZip.getAbsolutePath();
        String baseDirAp = tempDir.getAbsolutePath();
        String zeName = toZipAp.substring(baseDirAp.length(), toZipAp.length());
        return new ZipEntry(zeName);
    }

    private boolean isZipFile(String fileName)
    {
        String lo = fileName.toLowerCase();

        for (String zipe : zipFileExtensions)
        {
            if (lo.endsWith(zipe))
            {
                return true;
            }
        }
        return false;
    }

    private void prepareDirectories(String extractedFilePath)
    {
        File f = new File(extractedFilePath);
        f.getParentFile().mkdirs();
    }

    private void processDirectory(String tempDirPath, ZipEntry ze)
    {
        File dir = new File(tempDirPath + "/" + ze.getName());
        dir.mkdirs();
    }

    private void processFile(String tempDirPath, ZipEntry ze, InputStream is, byte[] buf)
            throws IOException
    {
        String fileName = ze.getName();
        String extractedFilePath = tempDirPath+"/"+removeFirstSlash(fileName);

        this.prepareDirectories(extractedFilePath);

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(extractedFilePath);

            // Simple copy===
            int _rd;

            while ((_rd=is.read(buf)) >= 0) {
                fos.write(buf, 0, _rd);
            }
            //===============

            is.close();
            
        } finally {
            if (fos!=null) {
                fos.close();
            }
        }

        if (this.isZipFile(fileName))
        {
            // So, ZIP-file unzipped, starting recursion
            ZipProcessor zp = new ZipProcessor(this.handler, this.readOnly);
            zp.process(new File(extractedFilePath));
        } else {
            this.handler.handle(new File(extractedFilePath));
        }
        
    }

    private String removeFirstSlash(String fileName)
    {
        if (fileName.length()>0 && fileName.startsWith("/"))
        {
            return fileName.substring(1);
        }
        return fileName;
    }

    private void unzipAssembly(File tempDir, File assembly)
            throws IOException
    {
        ZipInputStream zis = null;
        String tempDirPath = tempDir.getAbsolutePath();
        byte[] buf = new byte[4096];
        try
        {
            zis = new ZipInputStream(new FileInputStream(assembly));
            ZipEntry ze = null;
            while ((ze = zis.getNextEntry()) != null)
            {
                if (ze.isDirectory())
                {
                    this.processDirectory(tempDirPath, ze);
                } else
                {
                    this.processFile(tempDirPath, ze, new ZipInputStreamWrapper(zis), buf);
                }
            }
        } finally
        {
            if (zis != null)
            {
                zis.close();
            }
        }
    }

    private void writeEntryContent(File toZip, ZipOutputStream zos) throws IOException
    {
        FileInputStream fis = null;

        try{
            fis = new FileInputStream(toZip);

            byte[] buf = new byte[4096];
            int _rd;

            while ((_rd=fis.read(buf))>=0)
            {
                zos.write(buf, 0, _rd);
            }
        } finally {
            if (fis!=null)
            {
                fis.close();
            }
        }

    }

    private void zipAssembly(File tempDir, File assembly) throws IOException
    {
        ZipOutputStream zos = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(assembly));

            List<File> files = DirLister.getListing(tempDir);

            for (File toZip: files)
            {
                zos.putNextEntry(genZipEntry(tempDir, toZip));
                
                this.writeEntryContent(toZip, zos);

                zos.closeEntry();
            }

        } finally {
            if (zos!=null)
            {
                zos.close();
            }
        }
    }

    private File getNewDir()
    {
        File f = null;
        while (f==null)
        {
            String name = RandomUtil.generateDirectoryName("/tmp", RANDOM_FILE_NAME_LENGTH);
            f = new File(name);
            if (f.exists()) {
                f = null;
            }
        }

        return f;
    }
    
}
