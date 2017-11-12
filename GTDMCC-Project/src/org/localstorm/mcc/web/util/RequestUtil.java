package org.localstorm.mcc.web.util;

import org.localstorm.mcc.web.RequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 *
 * @author Alexey Kuznetsov
 */
public class RequestUtil 
{
    public static void setException(HttpServletRequest req, Exception e)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps  = new PrintStream(baos);
            e.printStackTrace(ps);
            ps.close();
            baos.close();
            req.setAttribute(RequestAttributes.EXCEPTION, baos.toString());
        } catch(IOException ex)
        {
            req.setAttribute(RequestAttributes.EXCEPTION, "Error while filling stack trace output: "+ex.getMessage());
        }
    }
}
