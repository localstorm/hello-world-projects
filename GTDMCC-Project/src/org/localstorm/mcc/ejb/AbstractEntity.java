package org.localstorm.mcc.ejb;

import java.io.Serializable;
import org.apache.commons.beanutils.BeanUtils;

/**
 * @author localstorm
 */
public class AbstractEntity implements Serializable {

    @Override
    public String toString() {
        try
        {
            return BeanUtils.describe(this).toString();
        } catch(Exception e) {
            return super.toString();
        }
    }


}
