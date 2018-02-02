/**
 * 
 */
package com.bcj.customercurd.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.bcj.customercurd.dao.CustomerDAO;
import com.bcj.customercurd.model.Customer;
import com.bcj.customercurd.service.CustomerService;

/**
 * @author Abhinay
 *
 */
@Path("/customer")
public class CustomerService {

	Customer cust = new Customer();
	CustomerDAO custdao = new CustomerDAO();

	/**
	 * Method to get customers
	 */
	@GET
	@Path("/{phonenumber}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@PathParam("phonenumber") String phonenumber) {

		System.out.println(phonenumber);

		Customer customer = custdao.getCustomer(phonenumber);

		System.out.println(customer.toString());

		return customer;
	}

	/**
	 * Method to delete customers
	 */
	@DELETE
	@Path("/{phonenumber}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteCustomers(@PathParam("phonenumber") int phonenumber) {
		return custdao.deleteCustomers(phonenumber);
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Customer addEmployee(Customer cust) {
		return custdao.addEmployee(cust);
	}
}
