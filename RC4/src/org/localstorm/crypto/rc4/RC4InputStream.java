package org.localstorm.crypto.rc4;

import org.localstorm.crypto.GammaGenerator;

import java.io.IOException;
import java.io.InputStream;

/**
 * RC4 stream cipher implementation.
 * @author LocalStorm
 * @version 1.0
 */
public class RC4InputStream extends InputStream
{
    private final InputStream cin;
    private final GammaGenerator gg;
    private int _read;
    private int _res;

    public RC4InputStream(InputStream is, String key)
    {
        cin = is;
        gg = RC4GammaGenerator.getGammaGenerator(key);
    }

    public int read() throws IOException
    {
        _read = cin.read();
        if (_read==-1) return -1;
        _res= _read^gg.next();
        if (_res<0){
            return 256+_res;
        }else{
            return _res;
        }
    }

    public void close() throws IOException
    {
        _res=0;
        _read=0;
        cin.close();
        gg.close();
    }
}
