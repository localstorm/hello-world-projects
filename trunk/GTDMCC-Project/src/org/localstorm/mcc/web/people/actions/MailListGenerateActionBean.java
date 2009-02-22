package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.people.maillists.MailListManager;
import org.localstorm.mcc.ejb.people.maillists.PregeneratedMailList;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.mcc.web.people.RequestAttributes;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/GenerateMailList")
public class MailListGenerateActionBean extends PeopleBaseActionBean
{
    

    @DefaultHandler
    public Resolution filling() {
        PeopleClipboard clip = super.getClipboard();
        
        MailListManager mlm = super.getMailListManager();

        PregeneratedMailList pml = mlm.generateMailList(clip.getPersons());

        if (pml.isReady()) {
            mlm.create(pml);
            clip.clearPersons();
            SessionUtil.clear(super.getSession(), SessionKeys.MAIL_LISTS);
            return NextDestinationUtil.getRedirection(super.getReturnPageBean());
        } else {
            super.getRequest().setAttribute(RequestAttributes.PREGENERATED_MAIL_LIST, pml);
            return new ForwardResolution(Views.VIEW_RESOLVE_EMAILS);
        }
    }
}
