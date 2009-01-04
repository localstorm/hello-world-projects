package org.localstorm.mcc.web;

import java.util.Map;
import java.util.TreeMap;
import net.sourceforge.stripes.action.OnwardResolution;

/**
 *
 * @author localstorm
 */
public class ReturnPageBean {
    
    private String pageToken;
    private Map<String, String> params;

    public ReturnPageBean(String pageToken) {
        this.pageToken = pageToken;
        this.params = new TreeMap<String, String>();
    }

    public String getPageToken() {
        return pageToken;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParam(String name, String value) {
        this.params.put(name, value);
    }

    public void appendParams(OnwardResolution res) {
        for (Map.Entry<String, String> e: this.params.entrySet()) {
            res.addParameter(e.getKey(), e.getValue());
        }
    }

}
