package root.client;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import root.entity.E_Employee;

public class Test {
	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure().addAnnotatedClass(E_Employee.class);
		SessionFactory factory = cfg.buildSessionFactory();
		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();

		try {
			// Fetch an Employee object using load (it will create a proxy in the
			// first-level cache)
			E_Employee emp1 = session.load(E_Employee.class, 1);
			System.out.println("First fetch (using load): " + emp1.getName());

			// Fetch the same Employee object again using load
			E_Employee emp2 = session.load(E_Employee.class, 1);
			System.out.println("Second fetch (using load): " + emp2.getName());

			// Check if the same object is returned
			System.out.println("Are emp1 and emp2 the same? " + (emp1 == emp2));

			// Commit transaction and close session
			session.beginTransaction().commit();
			
		} catch (ObjectNotFoundException e) {
			System.out.println("Employee not found: " + e.getMessage());
			transaction.rollback();
			session.close();
			factory.close();
		} finally {
			session.close();
			factory.close();
		}

	}
}
