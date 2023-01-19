package com.CrystalFinance.app.service;

import com.CrystalFinance.app.model.CustomerDetails;

public interface SanctionService {

	public Iterable<CustomerDetails> getCustomerbyStatus(String custloanstatus);

	public CustomerDetails updateCustomer(Integer cstid, CustomerDetails csd);

	public CustomerDetails generateSactionId(CustomerDetails customerDetails); /* , Integer customerid */

}
