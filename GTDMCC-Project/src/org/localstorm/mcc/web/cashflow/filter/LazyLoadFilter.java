package org.localstorm.mcc.web.cashflow.filter;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.ejb.cashflow.asset.Asset;
import org.localstorm.mcc.ejb.cashflow.asset.AssetManager;
import org.localstorm.mcc.ejb.cashflow.operations.OperationManager;
import org.localstorm.mcc.ejb.cashflow.targets.Target;
import org.localstorm.mcc.ejb.cashflow.targets.TargetManager;
import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.cashflow.actions.wrap.WrapUtil;
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
        
        if ( SessionUtil.isEmpty(sess, SessionKeys.ASSETS) ) {
            AssetManager am = ContextLookup.lookup(AssetManager.class,
                                                   AssetManager.BEAN_NAME);
            OperationManager om = ContextLookup.lookup(OperationManager.class,
                                                       OperationManager.BEAN_NAME);

            Collection<Asset> assets = am.findAssetsByOwner(user);
            assets = WrapUtil.wrapAssets(assets, om);
            SessionUtil.fill(sess, SessionKeys.ASSETS, assets);
        }

        if ( SessionUtil.isEmpty(sess, SessionKeys.TARGETS) ) {
            TargetManager tm = ContextLookup.lookup(TargetManager.class,
                                                    TargetManager.BEAN_NAME);

            Collection<Target> targets  =tm.findTargetsByOwner(user);
            
            SessionUtil.fill(sess, SessionKeys.TARGETS, targets);
        }
    }
    

}