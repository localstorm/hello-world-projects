/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.people;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="PERSON_GROUPS")
public class PersonGroup implements Serializable, Identifiable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    public PersonGroup() {
    }

    public PersonGroup(String name) {
        this.name = name;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
