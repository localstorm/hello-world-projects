package org.localstorm.mcc.web.people.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import org.localstorm.mcc.web.ReturnPageBean;
import org.localstorm.mcc.web.util.RedirectUrlBuilderUtil;

/**
 *
 * @author Alexey Kuznetsov
 */
public class NextDestinationUtil 
{

    public static RedirectResolution getRedirection(ReturnPageBean rpb) {
        if (rpb==null) {
            return NextDestinationUtil.getDefaultRedirection();
        }

        Pages rp = Pages.valueOf(rpb.getPageToken());

        RedirectResolution rr;
        switch(rp)
        {
            case PEOPLE_INDEX:
                rr = RedirectUrlBuilderUtil.redirect(PeopleViewActionBean.class);
                break;
            case GROUP_VIEW:
                rr = RedirectUrlBuilderUtil.redirect(PersonGroupViewActionBean.class);
                break;
            case PERSON_VIEW:
                rr = RedirectUrlBuilderUtil.redirect(PersonViewActionBean.class);
                break;
            default:
                rr = NextDestinationUtil.getDefaultRedirection();
                break;
        }

        rpb.appendParams(rr);
        return rr;
    }

    public static RedirectResolution getDefaultRedirection() {
          return RedirectUrlBuilderUtil.redirect(PeopleViewActionBean.class);
    }
}
