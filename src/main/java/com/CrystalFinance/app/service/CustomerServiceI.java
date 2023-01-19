package com.CrystalFinance.app.service;

import java.util.Optional;

import com.CrystalFinance.app.model.CustomerDetails;

public interface CustomerServiceI {

	public CustomerDetails saveCustomer(CustomerDetails cd);

	public Iterable<CustomerDetails> getCustomer();

	public Optional<CustomerDetails> findById(Integer enquid);

	public Iterable<CustomerDetails> getCustomerbyStatus(String custloanstatus);

	public CustomerDetails updateCustomer (CustomerDetails csd);

	public void deleteData(Integer csid);

}
