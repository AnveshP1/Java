package com.bcj.creditcardprocess;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.bcj.creditcardprocess.service.CreditCardService;

public class CreditCardMain {

	
	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");

	CreditCardService obj = (CreditCardService) context.getBean("cCardService");
		//CreditCardService  cCardService = new CreditCardService();

		obj.processCreditCard();		

	}

}
