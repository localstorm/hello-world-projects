package org.localstorm.mcc.ejb.lists;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="LIST_TYPES")
public class GTDListType implements Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY, generator="id")
    private Integer id;
    
    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;
    
    @Column(name="sort_order", unique=false, updatable=true, nullable=false )    
    private Integer sortOrder;

    public GTDListType() {
    }

    public GTDListType(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

}
