/**
 * 
 */
package com.bcj.customercurd.dao;

import java.io.Serializable;

import javax.ws.rs.PathParam;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.bcj.customercurd.model.Customer;
import com.bcj.customercurd.util.HibernateUtil;

/**
 * @author Abhinay
 *
 */
public class CustomerDAO {
	SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
	Session session = sessionFactory.getCurrentSession();

	public Customer getCustomer(String phoneno) {

		Transaction tx = session.beginTransaction();

		System.out.println("Hello ");
		Query query = session.createQuery("FROM Customer WHERE phoneno = :phoneno");

		query.setString("phoneno", phoneno);

		Customer customer = (Customer) query.uniqueResult();

		System.out.println("Hello dao");
		tx.commit();

		return customer;
	}

	public String deleteCustomers(int phoneno1) {

		Transaction tx = session.beginTransaction();

		System.out.println("delete"+phoneno1);
		/*Query query = session.createQuery("delete from Customer where phoneno= :phoneno");

		query.setString("phoneno", phoneno);*/
		
		Serializable custID = new Integer(phoneno1);
		Object persistentInstance = session.load(Customer.class, custID);
		if (persistentInstance != null) {
			session.delete(persistentInstance);
		}

		tx.commit();
		return "Sucessufully deleted";
	}

	public Customer addEmployee(Customer cust) {
		Transaction tx = session.beginTransaction();

		session.save(cust);

		tx.commit();
		return cust;

	}

}
