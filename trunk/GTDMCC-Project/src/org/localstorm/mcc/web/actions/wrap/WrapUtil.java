package org.localstorm.mcc.web.actions.wrap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.localstorm.mcc.ejb.tasks.Task;

/**
 *
 * @author Alexey Kuznetsov
 */
public class WrapUtil 
{
    public static Collection<TaskWrapper> genWrappers(Collection<Task> taskList,
                                                      Collection<Task> currentFp) 
    {
        Map<Integer, Boolean> mp = new HashMap<Integer, Boolean>();
        for (Task t: currentFp)
        {
            mp.put( t.getId(), Boolean.TRUE );
        }
        
        Collection<TaskWrapper> result = new ArrayList<TaskWrapper>(taskList.size());
        for (Task t: taskList)
        {
            result.add(new TaskWrapper(t, mp.containsKey(t.getId())));
        }
        
        return result;
    }
}
