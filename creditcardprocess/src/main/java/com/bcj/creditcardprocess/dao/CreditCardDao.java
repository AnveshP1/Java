package com.bcj.creditcardprocess.dao;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bcj.creditcardprocess.model.CreditCard;
import com.bcj.creditcardprocess.model.Customer;

@Repository
@Transactional
public class CreditCardDao {

	@Autowired
	private SessionFactory sessionFactory;
	// private Session session=sessionFactory.openSession();

	/*
	 * public void setSessionFactory(SessionFactory sessionFactory) {
	 * this.sessionFactory = sessionFactory; }
	 */

	List<Customer> custs;

	public List<Customer> getCustomerList() {
		org.hibernate.Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		custs = session.createQuery("FROM Customer WHERE status='New'").list();

		for (Iterator iterator = custs.iterator(); iterator.hasNext();) {
			Customer cust = (Customer) iterator.next();
			System.out.println(cust);
		}
		return custs;

	}

	public void persistCreditCard(CreditCard cCard, Customer cust) {
		ApplicationContext context = new FileSystemXmlApplicationContext("resources\\spring.xml");

		SessionFactory obj = (SessionFactory) context.getBean("sessionFactory");
		Session session = obj.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(cust);
		session.persist(cCard);
		tx.commit();

		System.out.println("customer saved sucessfully" + cCard);

	}

	public void updateCustomer(Customer cust) {

		ApplicationContext context = new FileSystemXmlApplicationContext("spring.xml");

		SessionFactory obj = (SessionFactory) context.getBean("sessionFactory");

		System.out.println(cust);

		Session session = obj.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(cust);

		System.out.println(" updated customer");
		tx.commit();
		// session.close();

	}

}
