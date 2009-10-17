package org.localstorm.platform;

public class PortInternal {
	private ComponentInternal parent;
	private String name;
	
	public PortInternal(String name, ComponentInternal parent) {
		if (name==null)
		{
			throw new NullPointerException("Given port name is null!");
		}
		this.parent = parent;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public ComponentInternal getParent() {
		return parent;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
