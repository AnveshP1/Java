package com.bcj.creditcardprocess.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Customer {

	@Id
	@GeneratedValue
	private int id;

	private String firstName;
	// private String middleName;
	private String lastName;
	// private String mobile;
	private String email;
	private String status;
	// private String dateOfBirth;
	private String ssn;
	private String annualIncome;
	private String houseMortage;
	
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/*
	 * public String getMiddleName() { return middleName; }
	 * 
	 * public void setMiddleName(String middleName) { this.middleName =
	 * middleName; }
	 */

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/*
	 * public String getMobile() { return mobile; }
	 * 
	 * public void setMobile(String mobile) { this.mobile = mobile; }
	 */

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/*
	 * public String getDateOfBirth() { return dateOfBirth; }
	 * 
	 * public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth =
	 * dateOfBirth; }
	 */
	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getHouseMortage() {
		return houseMortage;
	}

	public void setHouseMortage(String houseMortage) {
		this.houseMortage = houseMortage;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", status=" + status + ", ssn=" + ssn + ", annualIncome=" + annualIncome + ", houseMortage="
				+ houseMortage + "]";
	}

}
