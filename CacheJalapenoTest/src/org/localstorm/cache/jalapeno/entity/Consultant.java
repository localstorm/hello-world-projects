package org.localstorm.cache.jalapeno.entity;

public class Consultant extends Employee {
	private boolean isFullTime;
	
	public boolean isFullTime() {
		return isFullTime;
	}
	
	public void setFullTime(boolean isFullTime) {
		this.isFullTime = isFullTime;
	}
	
}
