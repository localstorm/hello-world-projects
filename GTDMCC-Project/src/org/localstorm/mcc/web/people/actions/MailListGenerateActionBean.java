package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.ejb.people.MailListManager;
import org.localstorm.mcc.ejb.people.entity.PregeneratedMailList;
import org.localstorm.mcc.web.SessionKeys;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;
import org.localstorm.mcc.web.people.PeopleClipboard;
import org.localstorm.mcc.web.people.RequestAttributes;
import org.localstorm.mcc.web.people.Views;
import org.localstorm.mcc.web.util.SessionUtil;

/**
 * @secure-by nil
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/nil/GenerateMailList")
public class MailListGenerateActionBean extends PeopleBaseActionBean
{

    @DefaultHandler
    public Resolution filling() {
        PeopleClipboard clip = super.getClipboard();
        
        MailListManager mlm = super.getMailListManager();

        PregeneratedMailList pml = mlm.generateMailList(clip.getPersons());

        if (pml.isReady()) {
            mlm.create(pml, "Goo-goo", super.getUser());
            clip.clearPersons();
            SessionUtil.clear(super.getSession(), SessionKeys.MAIL_LISTS);
            return NextDestinationUtil.getRedirection(super.getReturnPageBean());
        } else {
            super.getRequest().setAttribute(RequestAttributes.PREGENERATED_MAIL_LIST, pml);
            return new ForwardResolution(Views.VIEW_RESOLVE_EMAILS);
        }
    }
}