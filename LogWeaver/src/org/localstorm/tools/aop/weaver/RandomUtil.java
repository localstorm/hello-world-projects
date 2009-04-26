package org.localstorm.tools.aop.weaver;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.apache.commons.lang.RandomStringUtils;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RandomUtil 
{
    private static final String SECURE_RANDOM_ALGO = "SHA1PRNG";
    private static final SecureRandom sr;

    static
    {
        try {
            sr = SecureRandom.getInstance(SECURE_RANDOM_ALGO);
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
