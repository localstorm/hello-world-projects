package org.localstorm.tools.zip;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public class ZipInputStreamWrapper extends InputStream
{
    private ZipInputStream zis;

    public ZipInputStreamWrapper(ZipInputStream zis)
    {
        this.zis = zis;
    }

    @Override
    public int available()
            throws IOException
    {
        return zis.available();
    }

    @Override
    public void close()
            throws IOException
    {
        zis.closeEntry();
    }

    @Override
    public synchronized void mark(int readlimit)
    {
        zis.mark(readlimit);
    }

    @Override
    public boolean markSupported()
    {
        return zis.markSupported();
    }

    @Override
    public int read()
            throws IOException
    {
        return zis.read();
    }

    @Override
    public int read(byte[] b)
            throws IOException
    {
        return zis.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len)
            throws IOException
    {
        return zis.read(b, off, len);
    }

    @Override
    public synchronized void reset()
            throws IOException
    {
        zis.reset();
    }

    @Override
    public long skip(long n)
            throws IOException
    {
        return zis.skip(n);
    }

}
