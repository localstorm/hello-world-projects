package org.localstorm.mcc.ejb.people.entity;

import org.apache.commons.lang.StringUtils;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="PERSONS")
@NamedQueries({
    @NamedQuery(
        name = Person.Queries.FIND_BY_GROUP,
        query= "SELECT o.person FROM PersonToGroup o WHERE o.group=:group " +
        "ORDER BY o.person.lastName, o.person.name"
    )
})
public class Person extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @Column(name="lname", unique=false, updatable=true, nullable=true )
    private String lastName;

    @Column(name="pname", unique=false, updatable=true, nullable=true )
    private String pName;


    @Column(name="birth_date", unique=false, updatable=true, nullable=true )
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    private static final long serialVersionUID = 7496458575935458816L;

    public Person()
    {
    }

    public Person(Integer id)
    {
        this.id = id;
    }

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
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(lastName)) {
            sb.append(this.lastName);
            sb.append(' ');
            sb.append(name.charAt(0));
            sb.append('.');

            if (StringUtils.isNotEmpty(pName)) {
                sb.append(' ');
                sb.append(pName.charAt(0));
                sb.append('.');
            }

            return sb.toString();
        }

        if (StringUtils.isNotEmpty(pName)) {

            sb.append(name);
            sb.append(' ');
            sb.append(pName);

            return sb.toString();
        }

        return name;
    }

    public String getFullName() {
        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNotEmpty(lastName)) {
            sb.append(this.lastName);
            sb.append(' ');
        }

        sb.append(this.name);
        
        if (StringUtils.isNotEmpty(pName)) {
            sb.append(' ');
            sb.append(this.pName);

        }

        return sb.toString();
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

    public String getPatronymicName() {
        return pName;
    }

    public void setPatronymicName(String pName) {
        this.pName = pName;
    }

    public static interface Properties {
        public static final String GROUP = "group";
    }

    public static interface Queries {
        public static String FIND_BY_GROUP = "findPersonsToGroups";
    }

}