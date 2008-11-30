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
import javax.persistence.Table;
import org.localstorm.mcc.ejb.Identifiable;

/**
 *
 * @author localstorm
 */
@Entity
@Table(name="TARGETS")
public class Target implements Identifiable, Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="name", unique=false, updatable=true, nullable=false )
    private String name;

    @JoinColumn(name="valuable_id", nullable=false)
    @ManyToOne(fetch=FetchType.EAGER)
    private ValuableObject valuable;

    public Target() {
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

}
