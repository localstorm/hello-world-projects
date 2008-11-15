package org.localstorm.mcc.web.filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.contexts.Context;
import org.localstorm.mcc.ejb.contexts.ContextManager;
import org.localstorm.mcc.ejb.referenced.*;
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
        
        if ( SessionUtil.isEmpty(sess, SessionKeys.FP_FILTER_CONTEXT) ) {
            SessionUtil.fill(sess, SessionKeys.FP_FILTER_CONTEXT, new Integer(-1));
        }
        
        if ( SessionUtil.isEmpty(sess, SessionKeys.CONTEXTS) ) {
            ContextManager cm = ContextLookup.lookup(ContextManager.class, 
                                                     ContextManager.BEAN_NAME);
            Collection<Context> ctxList = cm.findByOwner(user);
            List<Context> ctxs = new ArrayList<Context>(ctxList);
            
            Collections.sort(ctxs, new Comparator<Context>() {
                @Override
                public int compare(Context o1, Context o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
            
            SessionUtil.fill(sess, SessionKeys.CONTEXTS, ctxs);
        }
        
        if ( SessionUtil.isEmpty(sess, SessionKeys.REFERENCE_OBJECTS) ) {
            RefObjectManager rom = ContextLookup.lookup(RefObjectManager.class, 
                                                         RefObjectManager.BEAN_NAME);
            SessionUtil.fill(sess, SessionKeys.REFERENCE_OBJECTS, rom.findOperativeByOwner(user));
        }
            
    }
    

}