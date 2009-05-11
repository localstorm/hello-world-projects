package org.localstorm.stocktracker.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Barrier class for huge requests prevention.
 * @author Alexey Kuznetsov
 */
public class LimitedInputStream extends InputStream
{
    private InputStream is;
    private long count;

    private final long threshold;

    /**
     * @param is Original unbounded input stream 
     * @param threshold Maximum number of bytes allowed to be read.
     */
    public LimitedInputStream(InputStream is, long threshold) {
        this.is        = is;
        this.count     = 0;
        this.threshold = threshold;
    }

    @Override
    public int available() throws IOException {
        return is.available();
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

    /**
     * @return Number of bytes that was actually read from original stream
     */
    public long getCount() {
        return count;
    }

    @Override
    public synchronized void mark(int readlimit) {
        throw new UnsupportedOperationException("Mark not supported!");
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    /**
     * @throws IOException general I/O-error
     * @throws TooLongStreamException if it was read more bytes than <i>threshold</i>
     */
    @Override
    public int read() throws IOException {
        
        int _rd = is.read();
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

    /**
     * @throws IOException general I/O-error
     * @throws TooLongStreamException if it was read more bytes than <i>threshold</i>
     */
    @Override
    public int read(byte[] b) throws IOException {
        int _rd = is.read(b);
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

    /**
     * @throws IOException general I/O-error
     * @throws TooLongStreamException if it was read more bytes than <i>threshold</i>
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int _rd = is.read(b, off, len);
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

    /**
     * @throws IOException general I/O-error
     * @throws TooLongStreamException if it was read more bytes than <i>threshold</i>
     */
    @Override
    public long skip(long n) throws IOException {
        long _rd = is.skip(n);
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

    private void checkOverflow() throws IOException {
        if (this.count>this.threshold) {
            throw new TooLongStreamException(threshold);
        }
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException("Mark not supported!");
    }
    
}
