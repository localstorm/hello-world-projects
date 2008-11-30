/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.cashflow;

import java.io.Serializable;
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
        query= "SELECT o FROM ValuableObject o WHERE o.owner=:owner"
    )
})
public class ValuableObject implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;


    public ValuableObject()
    {

    }

    public ValuableObject( User owner ) {
        this.owner = owner;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public static interface Queries
    {
        public static final String FIND_BY_OWNER          = "findVoByUser";
    }

    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
    
}