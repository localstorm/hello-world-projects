package org.localstorm.mcc.ejb.referenced.impl;

import java.util.Collection;
import javax.ejb.Stateless;
import org.localstorm.mcc.ejb.AbstractManager;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.referenced.RefObjectManagerLocal;
import org.localstorm.mcc.ejb.referenced.RefObjectManagerRemote;
import org.localstorm.mcc.ejb.referenced.ReferencedObject;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author Alexey Kuznetsov
 */

/**
 *
 * @author Alexey Kuznetsov
 */
@Stateless
public class RefObjectManagerBean extends AbstractManager<ReferencedObject>
                                  implements RefObjectManagerLocal, RefObjectManagerRemote
{

    public RefObjectManagerBean() {
        super(ReferencedObject.class);
    }
    
    @Override
    public Collection<ReferencedObject> findByContext(Context ctx) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Collection<ReferencedObject> findOperativeByUser(User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    

}
