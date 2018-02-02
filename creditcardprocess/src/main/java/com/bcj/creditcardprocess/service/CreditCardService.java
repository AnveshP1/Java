package com.bcj.creditcardprocess.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcj.creditcardprocess.dao.CreditCardDao;
import com.bcj.creditcardprocess.model.Customer;

@Service
public class CreditCardService {

	@Autowired
	private CreditCardDao cCardDao;

	public void setcCardDao(CreditCardDao cCardDao) {
		this.cCardDao = cCardDao;
	}

	public void processCreditCard() {

		List<Customer> customerList = cCardDao.getCustomerList();
		// creating a pool of 5 threads

			WorkerThread thread = new WorkerThread();
			thread.threadmain(customerList);
		
	}
}
