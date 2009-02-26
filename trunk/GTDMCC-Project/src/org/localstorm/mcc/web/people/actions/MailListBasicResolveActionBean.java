package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import org.localstorm.mcc.web.people.PeopleBaseActionBean;

/**
 *
 * @author Alexey Kuznetsov
 */
@UrlBinding("/actions/ppl/nil/BasicResolveMailList")
public class MailListBasicResolveActionBean extends PeopleBaseActionBean
{
    public Resolution handle() throws Exception
    {
        return null;
    }
}
