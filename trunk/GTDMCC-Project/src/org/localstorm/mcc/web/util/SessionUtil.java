package org.localstorm.mcc.web.util;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Alexey Kuznetsov
 */
public class SessionUtil 
{

    public static void clear(HttpSession sess, String key) {
        sess.removeAttribute(key);
    }

    public static void fill(HttpSession sess, String key, Object o) {
        sess.setAttribute(key, o);
    }

    public static boolean isEmpty(HttpSession sess, String key) {
        return sess.getAttribute(key)==null;
    }
    
    public static Object getValue(HttpSession sess, String key) {
        return sess.getAttribute(key);
    }
}
