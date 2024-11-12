package root.client;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import root.entity.E_Employee;

public class FirstLevelCache {
	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure().addAnnotatedClass(E_Employee.class);
		SessionFactory factory = cfg.buildSessionFactory();

		// First Session Object
		Session session1 = factory.openSession();

		try {

			E_Employee e1 = new E_Employee("Rohit Singh Thakur", 90.0);
			session1.save(e1);
			session1.beginTransaction().commit();

			// Fetch an Employee object (it will be cached in the first-level cache)
			E_Employee emp1 = session1.get(E_Employee.class, 1);
			System.out.println("First fetch: " + emp1);

			// Fetch the same Employee object again (Query will not be Fired)
			E_Employee emp2 = session1.get(E_Employee.class, 1);
			System.out.println("Second fetch: " + emp2);

			// Check if the same object is returned
			System.out.println("Are emp1 and emp2 the same? " + (emp1 == emp2));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session1.close();
		}

		// Creating Second Session Object

		Session session2 = factory.openSession();

		try {
			// Fetch an Employee object (it will be cached in the first-level cache)
			E_Employee emp3 = session2.get(E_Employee.class, 1);
			System.out.println("First fetch: " + emp3);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session2.close();
			factory.close();
		}

	}
}
