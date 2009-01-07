package org.localstorm.mcc.ejb.people;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.localstorm.mcc.ejb.Constants;

/**
 *
 * @author localstorm
 */
@Stateless
public class PersonManagerBean implements PersonManagerLocal,
                                         PersonManagerRemote
{

    public PersonManagerBean() {

    }
   

    @PersistenceContext(unitName=Constants.DEFAULT_PU)
    protected EntityManager em;
}
