package org.localstorm.tools.random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RandomUtil 
{
    private static SecureRandom sr;
    static
    {
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(sr.generateSeed(24));
        } catch(NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateRandomVariableName(int len)
    {
        return "$"+RandomStringUtils.random(len-1, 0, 0, true, false, null, sr);
    }

    public static String generateDirectoryName(String baseDir, int len)
    {
        return baseDir+"/li$"+RandomStringUtils.random(len-1, 0, 0, true, false, null, sr);
    }
}
