package org.localstorm.mcc.web.cashflow.filter.security;

import org.localstorm.mcc.ejb.users.User;
import org.localstorm.mcc.web.filter.SecurityCheckFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author Alexey Kuznetsov
 */
public class CashflowOperationSecurityCheckFilter extends SecurityCheckFilter
{
    public static final String OPERATION_ID_PARAM = "operationId";

    @Override
    @SuppressWarnings("unchecked")
    public void doFilter(HttpServletRequest req, HttpServletResponse res, User user)
            throws IOException,
                   ServletException
    {
        String oid = req.getParameter(OPERATION_ID_PARAM);

        if (oid!=null)
        {
            Integer operationId = Integer.parseInt(oid);
            SecurityUtil.checkOperationSecurity(req.getSession(true), operationId, user, log);
        }
    }

}