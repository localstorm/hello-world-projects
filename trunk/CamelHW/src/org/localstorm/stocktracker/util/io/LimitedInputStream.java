package org.localstorm.stocktracker.util.io;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public class LimitedInputStream extends InputStream
{
    private InputStream is;
    private long count;

    private final long threshold;

    public LimitedInputStream(InputStream is, long maxDataAmount) {
        this.is        = is;
        this.count     = 0;
        this.threshold = maxDataAmount;
    }

    @Override
    public int available() throws IOException {
        return is.available();
    }

    @Override
    public void close() throws IOException {
        is.close();
    }

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

    @Override
    public int read() throws IOException {
        
        int _rd = is.read();
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

    @Override
    public int read(byte[] b) throws IOException {
        int _rd = is.read(b);
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int _rd = is.read(b, off, len);
        if (_rd>0) {
            this.count+=_rd;
            checkOverflow();
        }

        return _rd;
    }

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
            throw new IOException("Stream overflow! Not more than ["+threshold+"] bytes expected!");
        }
    }

    @Override
    public synchronized void reset() throws IOException {
        throw new UnsupportedOperationException("Mark not supported!");
    }
    
}
