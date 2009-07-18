package org.localstorm.cache.jalapeno.entity;

import org.localstorm.cache.jalapeno.entity.base.PrintableEntity;

import com.jalapeno.annotations.Index;

@Index(name="DepIdx", propertyNames={"name"}, isPrimaryKey=true)
public class Department extends PrintableEntity {

	private String name;
	
	public Department() {
		this.name = null;
	}
	
	public Department(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
