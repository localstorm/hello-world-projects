package org.localstorm.mcc.ejb.people.dao;

import org.localstorm.mcc.ejb.people.Person;

/**
 *
 * @author localstorm
 */
public class PersonWrapper extends Person {

    private int remains;

    public PersonWrapper(Integer pid)
    {
        super(pid);
    }

    public int getRemains() {
        return remains;
    }

    public void setRemains(int remains) {
        this.remains = remains;
    }

}
