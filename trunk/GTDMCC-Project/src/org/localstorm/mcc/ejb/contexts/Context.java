/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.contexts;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="CONTEXTS")
public class Context implements Serializable {   
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="id")
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="sort_order", unique=false, updatable=true, nullable=false )    
    private Integer sortOrder;
    
    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private User owner;
    
    @Column(name="is_archived", unique=false, updatable=true, nullable=false )    
    private boolean archived;

    public Context() 
    {
    
    }

    public Context( String name, Integer sortOrder, User owner ) {
        this.name = name;
        this.sortOrder = sortOrder;
        this.owner = owner;
        this.archived = false;
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public boolean isArchived() {
        return archived;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
