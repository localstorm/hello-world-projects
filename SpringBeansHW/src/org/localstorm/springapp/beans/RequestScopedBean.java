package org.localstorm.springapp.beans;

/**
 * Created by IntelliJ IDEA.
 * User: localstorm
 * Date: Nov 14, 2009
 * Time: 3:38:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class RequestScopedBean {

    public RequestScopedBean(){
        System.out.println("RequestScopedBean()");
    }

    public String getMessage(){
        return "Goo";
    }
}
