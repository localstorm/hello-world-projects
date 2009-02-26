package org.localstorm.mcc.web.people.filter;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.PersonGroup;
import org.localstorm.mcc.ejb.people.PersonManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class LazyLoadFilter implements Filter 
{
    
    public LazyLoadFilter() {
    
    }

    @Override
    public void doFilter(ServletRequest _req, 
                         ServletResponse _res, 
                         FilterChain chain) throws IOException, ServletException 
    {
        performLazyLoad((HttpServletRequest) _req, (HttpServletResponse) _res );
        chain.doFilter(_req, _res);
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }

    private void performLazyLoad(HttpServletRequest req, HttpServletResponse res) {
        HttpSession sess = req.getSession(true);
        User user = (User) sess.getAttribute(SessionKeys.USER);
        
        if ( SessionUtil.isEmpty(sess, SessionKeys.PERSON_GROUPS) ) {
            PersonManager pm = ContextLookup.lookup(PersonManager.class,
                                                    PersonManager.BEAN_NAME);
            
            Collection<PersonGroup> pgList = pm.getGroups(user);
            SessionUtil.fill(sess, SessionKeys.PERSON_GROUPS, pgList);
        }

        if ( SessionUtil.isEmpty(sess, SessionKeys.ARCHIVE_PERSON_GROUPS) ) {
            PersonManager pm = ContextLookup.lookup(PersonManager.class,
                                                    PersonManager.BEAN_NAME);

            Collection<PersonGroup> pgList = pm.getArchivedGroups(user);
            SessionUtil.fill(sess, SessionKeys.ARCHIVE_PERSON_GROUPS, pgList);
        }

        if ( SessionUtil.isEmpty(sess, SessionKeys.MAIL_LISTS) ) {
            MailListManager mlm = ContextLookup.lookup(MailListManager.class,
                                                       MailListManager.BEAN_NAME);

            Collection<MailList> mll = mlm.getMailLists(user);
            SessionUtil.fill(sess, SessionKeys.MAIL_LISTS, mll);
        }

        if ( SessionUtil.isEmpty(sess, SessionKeys.ARCHIVE_MAIL_LISTS) ) {
            MailListManager mlm = ContextLookup.lookup(MailListManager.class,
                                                       MailListManager.BEAN_NAME);

            Collection<MailList> mll = mlm.getArchivedMailLists(user);
            SessionUtil.fill(sess, SessionKeys.ARCHIVE_MAIL_LISTS, mll);
        }
        
    }
    

}