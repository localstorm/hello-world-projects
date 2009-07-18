package org.localstorm.cache.jalapeno.entity;

import java.math.BigDecimal;


public class StaffFactory {
	public static Manager newManager(String name, String surname,
			BigDecimal salary, int rank, Department dep) {
		Manager mgr = new Manager();
		mgr.setDepartment(dep);
		mgr.setName(name);
		mgr.setSurname(surname);
		mgr.setSalary(salary);
		mgr.setRank(rank);
		return mgr;
	}

	public static Employee newEmployee(String name, String surname,
			BigDecimal salary, Department dep) {
		Employee emp = new Employee();
		emp.setDepartment(dep);
		emp.setName(name);
		emp.setSurname(surname);
		emp.setSalary(salary);
		return emp;
	}

	public static Consultant newConsultant(String name, String surname,
			BigDecimal salary, boolean fullTime, Department dep) {
		Consultant cons = new Consultant();
		cons.setDepartment(dep);
		cons.setFullTime(fullTime);
		cons.setName(name);
		cons.setSurname(surname);
		cons.setSalary(salary);
		return cons;
	}
}
