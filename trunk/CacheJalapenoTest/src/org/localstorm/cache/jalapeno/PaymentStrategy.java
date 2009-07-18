package org.localstorm.cache.jalapeno;

import java.math.BigDecimal;

import org.localstorm.cache.jalapeno.entity.Employee;

public interface PaymentStrategy {

	BigDecimal calc(Employee emp);
	
}
