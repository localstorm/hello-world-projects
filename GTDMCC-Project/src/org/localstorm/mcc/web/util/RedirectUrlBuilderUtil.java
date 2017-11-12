package org.localstorm.mcc.web.util;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.UrlBinding;

public class RedirectUrlBuilderUtil {

    private static final String APP_PREFIX = "https://mcc.kuznetsov.co";
    private static final String APP_ROOT = "/gtdmcc";

    public static RedirectResolution redirect(String action) {
        return new RedirectResolution(APP_PREFIX + APP_ROOT +  action, false);
    }

    public static RedirectResolution redirect(Class<? extends ActionBean> action) {
        UrlBinding urlBinding = action.getAnnotation(UrlBinding.class);

        if (urlBinding != null) {
            return new RedirectResolution(APP_PREFIX + APP_ROOT + urlBinding.value(), false);
        }

        throw new IllegalArgumentException("Action " + action.getName() + " doesn't have UrlBinding annotation");
    }

    public static String redirectUrlNoAppRoot(String action) {
        return APP_PREFIX + action;
    }
}
