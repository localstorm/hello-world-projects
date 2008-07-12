package org.localstorm.crypto;

/**
 * Gamma generator interface.
 */
public interface GammaGenerator
{
    void reset(String key);
    void close();
    byte next();
}
