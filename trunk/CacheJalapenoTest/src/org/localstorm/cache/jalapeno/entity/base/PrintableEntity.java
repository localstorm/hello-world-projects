package org.localstorm.cache.jalapeno.entity.base;

import org.apache.commons.beanutils.BeanUtils;

public abstract class PrintableEntity {
	@Override
	public String toString() {
		try {
			return BeanUtils.describe(this).toString();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
