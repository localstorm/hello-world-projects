package org.localstorm.mcc.ejb.people;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="PERSONS")
public class Person implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @Column(name="lname", unique=false, updatable=true, nullable=false )
    private String lastName;

    @Column(name="birth_date", unique=false, updatable=true, nullable=false )
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Override
    public Integer getId() {
        return this.id;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getName() {
        return this.name;
    }

    public String getShortName() {
        if ( name.length()>0 ) {
            return lastName+" "+name.charAt(0);
        } else {
            return lastName;
        }
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }
}