package org.localstorm.mcc.ejb.people.maillists;

import org.localstorm.mcc.ejb.people.persons.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="PERSONS_TO_MAIL_LISTS")
@NamedQueries({
})
public class PersonToMailList extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name="person_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private Person person;

    @JoinColumn(name="mail_list_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private MailList mailList;

    @JoinColumn(name="attribute_id", nullable=true)
    @ManyToOne(fetch=FetchType.LAZY)
    private Attribute attribute;

    @Column(name="attribute_value", unique=false, updatable=true, nullable=false )
    private String attributeValue;

    public PersonToMailList() {
    }

    public PersonToMailList(Person p, MailList ml, Attribute a) {
        this.person = p;
        this.mailList = ml;
        this.attribute = a;
        this.attributeValue = a.getVal();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Attribute getAttribute()
    {
        return attribute;
    }

    public void setAttribute(Attribute attribute)
    {
        this.attribute = attribute;

        if (attribute!=null) {
            this.attributeValue = attribute.getVal();
        }
    }

    public String getAttributeValue()
    {
        return attributeValue;
    }

    public MailList getMailList()
    {
        return mailList;
    }

    public void setMailList(MailList mailList)
    {
        this.mailList = mailList;
    }

    public static interface Queries
    {
    }

    public static interface Properties
    {
        public static final String PERSON = "person";
    }

}
