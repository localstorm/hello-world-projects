package org.localstorm.springapp.beans;

/**
 * User: localstorm
 * Date: Nov 14, 2009
 * Time: 3:38:58 PM
 */
public class RequestScopedBean {

    private User user;
    private Some some;

    public RequestScopedBean(){
        System.out.println("RequestScopedBean()");
    }

    public User getUser(){
        return user;
    }

    public void setUser(User u)
    {
        user = u;
    }

    public Some getSome() {
        return some;
    }

    public void setSome(Some some) {
        this.some = some;
    }
}
