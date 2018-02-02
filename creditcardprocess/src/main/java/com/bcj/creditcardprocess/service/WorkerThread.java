package com.bcj.creditcardprocess.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcj.creditcardprocess.dao.CreditCardDao;
import com.bcj.creditcardprocess.model.CreditCard;
import com.bcj.creditcardprocess.model.Customer;

@Service
public class WorkerThread implements Runnable {

	public WorkerThread() {

	}

	@Autowired
	CreditCardDao cCardDao = new CreditCardDao();

	WorkerThread(Customer cust2) {
		this.cust = cust2;

	}

	Customer cust;

	public void run() {

		System.out.println(Thread.currentThread().getName() + " (Start) message = " + cust.getFirstName());

		try {
			getCutomerDetailsByTextFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " (End)");

	}

	public synchronized void getCutomerDetailsByTextFile() throws IOException {

		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("details.txt");

BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

		String line = "";
		while ((line = br.readLine()) != null) {
			System.out.println(line);

			String[] details = line.split(" ");
			System.out.println(details[2]);
			
			System.out.println(cust.getSsn());

			if (details[0].equals(cust.getFirstName()) && details[1].equals(cust.getLastName())
					&& details[2].equals(cust.getSsn())) {
				
				System.out.println("ssn had");

				if (Integer.parseInt(details[3]) > 700 && Integer.parseInt(cust.getAnnualIncome()) > 30000) {

					CreditCard card = new CreditCard();
					card.setCreditLimit(5000);
					cust.setStatus("approved");
					card.setCustomer(cust);
					generateCardNumber(card, cust);

				} else if (Integer.parseInt(details[3]) > 600 && Integer.parseInt(details[3]) <= 700
						&& Integer.parseInt(cust.getAnnualIncome()) > 9999) {

					CreditCard card = new CreditCard();
					card.setCreditLimit(3000);
					card.setCustomer(cust);
					cust.setStatus("approved");
					generateCardNumber(card, cust);

				} else if (Integer.parseInt(details[3]) > 500 && Integer.parseInt(details[3]) <= 600
						&& Integer.parseInt(cust.getAnnualIncome()) > 19990) {

					CreditCard card = new CreditCard();
					card.setCreditLimit(1500);
					card.setCustomer(cust);
					cust.setStatus("approved");
					generateCardNumber(card, cust);

				}

				else {
					System.out.println(cCardDao);
					cust.setStatus("Declined");
					cCardDao.updateCustomer(cust);
					String subject = "Credit Card Application Status";
					String msg = "Hi  " + cust.getFirstName() + "\n"
							+ " Your credit card application is processed and sorry to say that your credit score is too low to approve it "
							+ "\n" + "          Thank you ";
					send("citibank.creditcard007@gmail.com", "Citibank@123", cust.getEmail(), subject, msg);
					System.out.println("Declined");
				}

				break;

			}

		}

	}

	private void send( String from, String password,String to,String sub,String msg) {

		// Get properties object
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		// get Session
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(sub);
			message.setText(msg);
			// send message
			Transport.send(message);
			System.out.println("message sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public void threadmain(List<Customer> customerList) {

		ExecutorService executor = Executors.newFixedThreadPool(1);

		for (Iterator iterator = customerList.iterator(); iterator.hasNext();) {
			Customer cust = (Customer) iterator.next();

			Runnable worker = new WorkerThread(cust);
			executor.execute(worker);// calling execute method of
										// ExecutorService
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		System.out.println("Finished all threads");

	}

	private void generateCardNumber(CreditCard cCard, Customer cust) {

		Random rand = new Random();

		String cardNumber = Integer.toString(rand.nextInt(9999) + 1000) + " "
				+ Integer.toString(rand.nextInt(9999) + 1000) + " " + Integer.toString(rand.nextInt(9999) + 1000) + " "
				+ Integer.toString(rand.nextInt(9999) + 1000);

		String cvv = Integer.toString(rand.nextInt(999) + 100);

		Calendar cal = Calendar.getInstance();
		Date today = cal.getTime();
		cal.add(Calendar.YEAR, 3); // to get previous year add -1
		Date next = cal.getTime();

		SimpleDateFormat adf = new SimpleDateFormat("MM/YY");
		String expiryDate = adf.format(next);

		cCard.setCardNumber(cardNumber);
		cCard.setCvv(cvv);
		cCard.setExpiryDate(expiryDate);

		cCardDao.persistCreditCard(cCard, cust);

		String subject = "Credit Card  Appilication Status";
		String msg = "Hi  " + cust.getFirstName() + "\n"
				+ " Your credit card application is processed and it is approved. Your credit limit is "
				+ cCard.getCreditLimit() + "  and your card number is  " + cCard.getCardNumber() + " \n"
				+ "          Thank you ";
		send("citibank.creditcard007@gmail.com", "Citibank@123", cust.getEmail(), subject, msg);

	}

}
