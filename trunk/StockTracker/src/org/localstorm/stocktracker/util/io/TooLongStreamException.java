package org.localstorm.stocktracker.util.io;

import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TooLongStreamException extends IOException
{
    private static final long serialVersionUID = 1L;
    
    public TooLongStreamException(long maxExpected) {
        super("["+maxExpected+"] bytes limit exceeded.");
    }
}
