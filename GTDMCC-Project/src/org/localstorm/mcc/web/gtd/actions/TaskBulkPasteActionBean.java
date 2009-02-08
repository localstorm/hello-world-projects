package org.localstorm.mcc.web.gtd.actions;

import org.localstorm.mcc.web.gtd.GtdBaseActionBean;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.gtd.lists.GTDList;
import org.localstorm.mcc.ejb.gtd.lists.ListManager;
import org.localstorm.mcc.ejb.gtd.tasks.Task;
import org.localstorm.mcc.ejb.gtd.tasks.TaskManager;
import org.localstorm.mcc.web.Clipboard;

/**
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/gtd/list/BulkPasteTask")
public class TaskBulkPasteActionBean extends GtdBaseActionBean
{
    @Validate( required=true )
    private int listId;
    
    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    
    @DefaultHandler
    public Resolution pasting() throws Exception {
        
        ListManager lm = super.getListManager();
        TaskManager tm = super.getTaskManager();
        Clipboard clip = super.getClipboard();

        GTDList dst = lm.findById(this.getListId());

        for (Task task : clip.getTasks())
        {
            task.setList(dst);
            tm.update(task);
        }

        clip.clearTasks();
        
        RedirectResolution rr = new RedirectResolution(ListViewActionBean.class);
        {
            rr.addParameter(ListViewActionBean.IncommingParameters.LIST_ID, dst.getId());
        }
        
        return rr;
    }
}
