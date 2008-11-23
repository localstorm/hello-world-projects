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

/**
 * @author localstorm
 */
@Entity
@Table(name="ASSETS")
@NamedQueries({
    @NamedQuery(
        name = Asset.Queries.FIND_BY_OWNER,
        query= "SELECT o FROM Asset o WHERE o.valuable.owner=:owner ORDER BY o.name"
    )
})
public class Asset implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @JoinColumn(name="valuable_id", nullable=false)
    @ManyToOne(fetch=FetchType.LAZY)
    private ValuableObject valuable;

    public Asset() {
    }

    @Override
    public Integer getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getName() {
        return name;
    }

    public ValuableObject getValuable() {
        return valuable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValuable(ValuableObject valuable) {
        this.valuable = valuable;
    }

    public static interface Queries
    {
        public static final String FIND_BY_OWNER          = "findAssetsByUser";
    }

    public static interface Properties
    {
        public static final String OWNER = "owner";
    }
}
