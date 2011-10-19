package org.localstorm.marketwatch.alert;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

class SmsNotificationUtil {

    private final static String password = "WherWolF";
    private final static String user = "localstorm@gmail.com";
    private final static String apiId = "3337956";
    private final static String to = "79163420445";

    public static void notify(String msg) throws Exception {
        String url = "http://api.clickatell.com/http/sendmsg?";
        url = add(url, "user", user);
        url = add(url, "password", password);
        url = add(url, "api_id", apiId);
        url = add(url, "to", to);
        url = add(url, "text", msg);

        //System.out.println(url);
        URL u = new URL(url);
        URLConnection c = u.openConnection();
        InputStream is = c.getInputStream();
        IOUtils.copy(is, System.out);
        IOUtils.closeQuietly(is);
        System.out.println();
    }

    private static String add(String url, String param, String value) throws Exception {
        return url+URLEncoder.encode(param, "UTF-8")+"="+URLEncoder.encode(value, "UTF-8")+"&";
    }

}
