package org.localstorm.stocktracker.util.io;

import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TooLongStreamException extends IOException
{
    public TooLongStreamException(long maxExpected)
    {
        super("["+maxExpected+"] bytes limit exceeded.");
    }
}
