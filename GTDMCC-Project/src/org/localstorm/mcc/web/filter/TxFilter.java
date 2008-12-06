package org.localstorm.mcc.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.localstorm.mcc.ejb.ContextLookup;
import org.localstorm.mcc.web.gtd.Views;
import org.localstorm.mcc.web.util.RequestUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class TxFilter implements Filter 
{

    public TxFilter() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        
        UserTransaction ut = null;
        
        try
        {
            try {
                ut = ContextLookup.lookupTransaction();
                ut.begin();
                    chain.doFilter( req, resp );
                ut.commit();
            } catch (Exception e) {
                e.printStackTrace();

                ut.setRollbackOnly();
                
                RequestUtil.setException((HttpServletRequest) req, e);
                req.getRequestDispatcher( Views.ERROR ).forward( req, resp );

            } finally {
                if (ut!=null && 
                    Status.STATUS_COMMITTED!=ut.getStatus() && 
                    ut.getStatus()!=Status.STATUS_NO_TRANSACTION) 
                {
                    ut.rollback();
                }
            }
        } catch(SystemException e) {
            RequestUtil.setException((HttpServletRequest) req, e);
            req.getRequestDispatcher( Views.ERROR ).forward( req, resp );
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    
    }
    

}
