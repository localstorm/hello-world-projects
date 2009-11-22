package org.localstorm.springapp.beans;

/**
 * User: localstorm
 * Date: Nov 17, 2009
 * Time: 10:00:25 AM
 */
public class Some {

    private String login;

    public Some() {
        login = "Anonymous";
    }

    public String getName() {
        return login;
    }

    public void setName(String name){
        login = name;
    }
}