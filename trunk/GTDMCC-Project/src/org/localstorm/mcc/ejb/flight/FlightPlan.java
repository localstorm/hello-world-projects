package org.localstorm.mcc.ejb.flight;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.localstorm.mcc.ejb.Identifiable;
import org.localstorm.mcc.ejb.Retireable;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */
@Entity
@Table(name="FLIGHT_PLANS")
@NamedQueries({
    @NamedQuery(
        name = FlightPlan.Queries.FIND_CURRENT_BY_USER,
        query= "SELECT o FROM FlightPlan o WHERE o.owner=:owner and o.archived=false"
    )
})
public class FlightPlan implements Identifiable, Retireable, Serializable
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    
    @JoinColumn(name="user_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private User owner;
    
    @Column(name="creation", unique=false, updatable=true, nullable=false )    
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;
    
    @Column(name="is_archive", unique=false, updatable=true, nullable=false )    
    private boolean archived;

    public FlightPlan() {
    }

    public FlightPlan(User owner) {
        this.id = null;
        this.owner = owner;
        this.creation = new Date();
        this.archived = false;
    }
    
    
    
    @Override
    public Integer getId() {
        return id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    @Override
    public boolean isArchived() {
        return archived;
    }

    @Override
    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
    public static interface Queries
    {
        public static final String FIND_CURRENT_BY_USER = "findCurrentByUser";
    }
    
    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
}
