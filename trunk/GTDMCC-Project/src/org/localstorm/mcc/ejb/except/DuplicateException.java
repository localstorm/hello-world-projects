/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.except;

/**
 *
 * @author localstorm
 */
public class DuplicateException extends Exception {

    public DuplicateException() {
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(String message) {
        super(message);
    }
    

}
