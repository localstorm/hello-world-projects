/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.cashflow;

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
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.localstorm.mcc.ejb.Identifiable;
import org.localstorm.mcc.ejb.Retireable;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="VALUABLE_OBJECTS")
@NamedQueries({
    @NamedQuery(
        name = ValuableObject.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM ValuableObject o WHERE o.owner=:owner and o.archived=false"
    ),
    @NamedQuery(
        name = ValuableObject.Queries.FIND_BY_OWNER_ARCHIVED,
        query= "SELECT o FROM ValuableObject o WHERE o.owner=:owner and o.archived=true"
    )
})
public class ValuableObject implements Identifiable, Retireable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;

    @Column(name="is_archived", unique=false, updatable=true, nullable=false )
    private boolean archived;

    public ValuableObject()
    {

    }

    public ValuableObject( String name, User owner ) {
        this.name = name;
        this.owner = owner;
        this.archived = false;
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isArchived() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setArchived(boolean archived) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public static interface Queries
    {
        public static final String FIND_BY_OWNER          = "findVoByUser";
        public static final String FIND_BY_OWNER_ARCHIVED = "findVoByUserArchived";
    }

    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
    
}