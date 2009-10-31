/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.cashflow.actions.wrap;

import java.math.BigDecimal;
import org.localstorm.mcc.ejb.cashflow.entity.Asset;
import org.localstorm.mcc.ejb.cashflow.entity.Cost;
import org.localstorm.mcc.ejb.cashflow.entity.ValuableObject;

/**
 *
 * @author localstorm
 */
public class AssetWrapper extends Asset {

    private Asset asset;
    private BigDecimal amount;
    private Cost cost;
    private BigDecimal netWealth;
    private BigDecimal iCost;
    private BigDecimal balance;
    private BigDecimal revenuAmount;
    private static final long serialVersionUID = -2842467359188553256L;

    public AssetWrapper(Asset asset, 
                        BigDecimal amount,
                        Cost cost,
                        BigDecimal iCost,
                        BigDecimal balance,
                        BigDecimal revenuAmount) {
        if (amount==null) {
            throw new NullPointerException("Amount is null!");
        }

        if (cost==null) {
            throw new NullPointerException("Cost is null!");
        }
        
        this.asset     = asset;
        this.amount    = amount;
        this.cost      = cost;
        this.iCost     = iCost;
        this.netWealth = amount.multiply(cost.getSell());
        this.balance   = balance;
        this.revenuAmount = revenuAmount;
    }

    @Override
    public Integer getId() {
        return asset.getId();
    }

    @Override
    public String getName() {
        return asset.getName();
    }

    @Override
    public ValuableObject getValuable() {
        return asset.getValuable();
    }

    @Override
    public void setName(String name) {
        asset.setName(name);
    }

    @Override
    public void setValuable(ValuableObject valuable) {
        asset.setValuable(valuable);
    }

    public BigDecimal getAmount()
    {
        return this.amount;
    }

    public Cost getCurrentCost()
    {
        return this.cost;
    }

    public BigDecimal getNetWealth()
    {
        return this.netWealth;
    }

    public BigDecimal getInvestmentsCost()
    {
        return this.iCost;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public BigDecimal getRevenuAmount() {
        return revenuAmount;
    }


    
}
