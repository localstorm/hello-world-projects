package org.localstorm.mcc.web;


import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.ejb.users.UserManager;

/**
 *
 * @author Alexey Kuznetsov
 */
public class BaseActionBean implements ActionBean 
{
    private ActionBeanContext context;
    
    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }
    
    protected HttpSession getSession()
    {
        return this.getContext().getRequest().getSession(true);
    }

    protected UserManager getUserManager() {
        return ContextLookup.lookup(UserManager.class, UserManager.BEAN_NAME);
    }
    
    protected User getUser() 
    {
        User user = (User)this.getSession().getAttribute(SessionKeys.USER);
            
        if (user==null) {
            throw new NullPointerException("USER IS NULL!");
        }
        
        return user;
    }
  
 }
