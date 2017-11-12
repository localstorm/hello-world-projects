/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.ejb.cashflow.entity;

import org.localstorm.mcc.ejb.AbstractEntity;
import org.localstorm.mcc.ejb.Identifiable;
import org.localstorm.mcc.ejb.users.User;

import javax.persistence.*;
import java.io.Serializable;

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
public class ValuableObject extends AbstractEntity implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;

    @Column(name="is_used_in_balance", updatable=true, nullable=false )
    private boolean usedInBalance;

    @Column(name="is_debt", updatable=true, nullable=false )
    private boolean debt;
    private static final long serialVersionUID = -1097606781530978130L;


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

    public boolean isUsedInBalance() {
        return usedInBalance;
    }

    public void setUsedInBalance(boolean usedInBalance) {
        this.usedInBalance = usedInBalance;
    }

    public boolean isDebt()
    {
        return debt;
    }

    public void setDebt(boolean debt)
    {
        this.debt = debt;
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