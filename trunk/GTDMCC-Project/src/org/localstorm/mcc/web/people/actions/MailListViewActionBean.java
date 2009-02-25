package org.localstorm.mcc.web.people.actions;

import java.util.Collection;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.MailList;
import org.localstorm.mcc.ejb.people.entity.PersonToMailList;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.Views;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/ml/ViewMailList")
public class MailListViewActionBean extends PeopleBaseActionBean
{
    @Validate(required=true)
    private Integer mailListId;

    private Collection<PersonToMailList> mailListContent;

    public Integer getMailListId()
    {
        return mailListId;
    }

    public void setMailListId(Integer mailListId)
    {
        this.mailListId = mailListId;
    }

    public void setMailListContent(Collection<PersonToMailList> mailListContent)
    {
        this.mailListContent = mailListContent;
    }

    public Collection<PersonToMailList> getMailListContent()
    {
        return mailListContent;
    }

    @DefaultHandler
    public Resolution filling() {
        
        MailListManager mlm = super.getMailListManager();
        MailList ml = mlm.find(this.getMailListId());
        
        this.setMailListContent(mlm.findMailListContent(ml));

        ReturnPageBean rpb = new ReturnPageBean(Pages.MAIL_LIST_VIEW.toString());
        {
            rpb.setParam(IncommingParameters.MAIL_LIST_ID, this.getMailListId().toString());
        }

        super.setReturnPageBean(rpb);

        return new ForwardResolution(Views.VIEW_MAIL_LIST);
    }


    public static interface IncommingParameters {
        public static final String MAIL_LIST_ID = "mailListId";
    }

}
