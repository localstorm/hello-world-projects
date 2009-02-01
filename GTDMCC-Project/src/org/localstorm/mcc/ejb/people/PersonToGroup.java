package org.localstorm.mcc.ejb.people;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="PERSONS_TO_GROUPS")
public class PersonToGroup extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name="person_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Person person;

    @JoinColumn(name="group_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private PersonGroup group;

    public PersonToGroup() {
    }

    public PersonToGroup(Person p, PersonGroup g) {
        this.person = p;
        this.group  = g;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Person getPerson() {
        return person;
    }

    public PersonGroup getGroup() {
        return group;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setGroup(PersonGroup group) {
        this.group = group;
    }
    
}