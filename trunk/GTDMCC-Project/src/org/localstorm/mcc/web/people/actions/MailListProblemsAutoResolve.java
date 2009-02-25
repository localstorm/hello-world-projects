package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/AutoResolveMailListProblems")
public class MailListProblemsAutoResolve extends PeopleBaseActionBean
{
    @Validate(required=true)
    private Integer mailListId;

    public Integer getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    @DefaultHandler
    public Resolution filling() throws Exception
    {
        MailListManager mlm = super.getMailListManager();
        MailList ml = mlm.find(this.getMailListId());

        mlm.tryAutoResolveBrokenEmails(ml);

        RedirectResolution rr = new RedirectResolution(MailListViewActionBean.class);
        {
            rr.addParameter(MailListViewActionBean.IncommingParameters.MAIL_LIST_ID, this.getMailListId());
        }
        return rr;
    }
}
