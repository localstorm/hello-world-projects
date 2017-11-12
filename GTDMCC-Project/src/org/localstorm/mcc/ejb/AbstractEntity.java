package org.localstorm.mcc.ejb;

import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;

/**
 * @author localstorm
 */
public class AbstractEntity implements Serializable {
    private static final long serialVersionUID = 5105960921172895354L;

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
