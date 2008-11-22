/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.localstorm.mcc.web.util;

import java.util.Collection;
import java.util.Iterator;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author localstorm
 */
public class FilterUtil {

    public static void applyContextFilter(Collection<Task> tasks, Integer ctxId)
    {
        if ( ctxId<0 ) {
            return;
        }

        for (Iterator<Task> it = tasks.iterator(); it.hasNext(); ) {
            Task t = it.next();
            if (!ctxId.equals(t.getList().getContext().getId())) {
                it.remove();
            }
        }
    }
}