package org.localstorm.crypto.rc4;

import org.localstorm.crypto.GammaGenerator;

/**
 * RC4 stream cipher implementation.
 * @author LocalStorm
 * @version 1.0
 */
public class RC4GammaGenerator implements GammaGenerator
{
    private static int slen = 256;
    private char[] s = new char[slen];
    private static char[] nulls = new char[slen];
    private char swp;
    private int icounter;
    private int jcounter;

    private void swap(char[] arr, char ind1, char ind2)
    {
            swp = arr[ind1];
            arr[ind1] = arr[ind2];
            arr[ind2] = swp;
        }

    public static GammaGenerator getGammaGenerator(String key)
    {
            return new RC4GammaGenerator().init(key);
        }

    private RC4GammaGenerator init(String key)
    {
        jcounter = icounter = 0;
        char k[] = new char[256];
        char keyBytes[] = key.toCharArray();

        if ( key.length() > 256 ) throw new RuntimeException("Key too long. It must not exceed 256 bytes length.");
        for ( char i = 0 ; i < slen ; i++ ){
            s[i] = i;
        }
        for (int i=0;i<255;i++){
            k[i] = keyBytes[i%keyBytes.length];
        }

        char j=0;
        for (char i=0; i<256; i++){
            j = (char)((j+s[i]+k[i])%256);
            swap(s,i,j);
        }
        return this;
   }

    public void reset(String key)
    {
         this.init(key);
    }

    public void close()
    {
         swp = 0;
         System.arraycopy(nulls, 0, s, 0, slen);
         icounter = jcounter = 0;
    }

    public byte next()
    {
        icounter = (icounter+1)&0x00FF;
        jcounter = (jcounter+s[icounter])&0x00FF;
        swap(s,(char)icounter,(char)jcounter);
        return (byte)s[(s[icounter]+s[jcounter])&0x00FF];
     }

}
