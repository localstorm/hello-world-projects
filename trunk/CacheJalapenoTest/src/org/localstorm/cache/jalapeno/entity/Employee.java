package org.localstorm.cache.jalapeno.entity;

import java.math.BigDecimal;

import org.localstorm.cache.jalapeno.entity.base.PrintableEntity;

public class Employee extends PrintableEntity {
  
	private String name;
	private String surname;
	
	private Department department;
	private BigDecimal salary;
	
	public Employee()
	{
		this.name 		= null;
		this.department = null;
		this.salary 	= null;
	}

	public Department getDepartment() {
		return department;
	}
	
	public String getName() {
		return name;	
	}
	
	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFullName() {
		return getName()+" "+getSurname();
	}
}
