package org.localstorm.cache.jalapeno;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.localstorm.cache.jalapeno.entity.Consultant;
import org.localstorm.cache.jalapeno.entity.Department;
import org.localstorm.cache.jalapeno.entity.Employee;
import org.localstorm.cache.jalapeno.entity.Manager;
import org.localstorm.cache.jalapeno.entity.StaffFactory;

import com.jalapeno.ApplicationContext;
import com.jalapeno.ObjectManager;

public class Main {
	
	// This is just to make things "right"
	private static Map<Class<? extends Employee>, PaymentStrategy> strategies;

	@SuppressWarnings("unchecked")
	public static void main(String... args) throws Exception {
		initPaymentStrategies();
		ObjectManager objManager = getObjectManager();
		initDb(objManager);

		// Note, we use superclass to retrieve employees of any kind
		Iterator<Employee> it = objManager.openByQuery(Employee.class, null, null);

		BigDecimal total = new BigDecimal(0);
		while (it.hasNext()) {
			
			Employee e = it.next();
			BigDecimal pay = getPayment(e);
			total = total.add(pay);

			// It may seems confusing, but classes doesn't have ID fields :)
			System.out.println(e.getFullName() + " ["+objManager.getId(e)+"] : " + pay.doubleValue());
		}

		System.out.println("------------------");
		System.out.println("Total: "+total.doubleValue());

		objManager.close();
	}

	private static ObjectManager getObjectManager() throws Exception {
		String url = "jdbc:Cache://localhost:1972/USER";
		String user = "_SYSTEM";
		String pwd = "SYS";
		return ApplicationContext.createObjectManager(url, user, pwd);
	}

	private static void initDb(ObjectManager objManager) throws Exception {
		objManager.extentManager().killExtent(Manager.class);
		objManager.extentManager().killExtent(Consultant.class);
		objManager.extentManager().killExtent(Employee.class);

		Department dep = new Department("IT Crowd");
		Manager mgr = StaffFactory.newManager("Vasya", "Pupkin", 
								 new BigDecimal(10000), 10,
								 dep);
		Consultant cns = StaffFactory.newConsultant("Petya", "Fortochkin", 
									   new BigDecimal(8000), true, dep);
		Consultant cns2 = StaffFactory.newConsultant("Vanya", "Durik", 
										new BigDecimal(8000), false, dep);
		Employee emp = StaffFactory.newEmployee("Poor", "Jorik", new BigDecimal(1000), dep);

		objManager.insert(mgr, true);
		objManager.insert(cns, true);
		objManager.insert(cns2, true);
		objManager.insert(emp, true);
	}

	private static void initPaymentStrategies() {
		strategies = new HashMap<Class<? extends Employee>, PaymentStrategy>();
		strategies.put(Employee.class, new PaymentStrategy() {
			@Override
			public BigDecimal calc(Employee emp) {
				return emp.getSalary();
			}
		});

		strategies.put(Manager.class, new PaymentStrategy() {
			@Override
			public BigDecimal calc(Employee emp) {
				return emp.getSalary().multiply(
						new BigDecimal(1 + 0.05 * ((Manager) emp).getRank()));
			}
		});

		strategies.put(Consultant.class, new PaymentStrategy() {
			@Override
			public BigDecimal calc(Employee emp) {
				Consultant c = (Consultant) emp;
				if (c.isFullTime()) {
					return c.getSalary().multiply(new BigDecimal(1.1));
				} else {
					return c.getSalary();
				}
			}
		});
	}

	private static BigDecimal getPayment(Employee e) {
		Class<? extends Employee> c = e.getClass();
		PaymentStrategy ps = strategies.get(c);
		return ps.calc(e);
	}
}
