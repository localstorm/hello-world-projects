package org.localstorm.mcc.ejb.gtd.tasks;

import org.localstorm.mcc.ejb.gtd.tasks.dao.FiredHintsReportBean;
import java.util.Collection;
import org.localstorm.mcc.ejb.BaseManager;
import org.localstorm.mcc.ejb.users.User;

/**
 *
 * @author localstorm
 */
public interface HintManager extends BaseManager<Hint>
{
    public static final String BEAN_NAME = "HintManagerBean";

    public Collection<Hint> getHintsForTask(Task t);
    public FiredHintsReportBean getFiredHintsReport(User user);
    public void updateHintsForTask(Task t);
}
