package org.localstorm.mcc.ejb.users;

import java.io.Serializable;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author localstorm
 */
@Entity
@Table(name="USERS")
public class User implements Serializable 
{
     @Id
     @Column(name="id", unique=true, updatable=false )
     private Integer id;
     
     @Column(name="fname")
     private String firstName;
     
     @Column(name="login", unique=true, updatable=false )
     private String login;
     
     @Column(name="lname")
     private String lastName;
     
     @Column(name="pass_hash")
     private String passHash;
     
     @Column(name="is_blocked")
     private boolean blocked;

    public User() 
    {
        
    }
    
    public User(String login, String firstName, String lastName, String passHash) 
    {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.passHash  = passHash;
        this.blocked   = false;
        this.login     = login;
        this.id        = (new Random()).nextInt();
    }
     
    public String getFirstName() 
    {
        return firstName;
    }

    public String getLastName() 
    {
        return lastName;
    }

    public String getPasswordHash() 
    {
        return passHash;
    }
 

    public boolean isBlocked() 
    {
        return blocked;
    }

    public String getLogin() {
        return login;
    }
    
    public Integer getId() 
    {
        return id;
    }

    public void setId(Integer id) 
    {
        this.id = id;
    }
    
    public void setPasswordHash(String hash) 
    {
        this.passHash = hash;
    }

    public void setBlocked(boolean blocked) 
    {
        this.blocked = blocked;
    }

    public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

}
