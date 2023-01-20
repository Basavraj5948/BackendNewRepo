package com.CrystalFinance.app.service;

import java.util.Optional;

import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.SanctionLetter;

public interface SanctionService {

	public Iterable<CustomerDetails> getCustomerbyStatus(String custloanstatus);

	public CustomerDetails updateCustomer(Integer cstid, CustomerDetails csd);

	public Optional<CustomerDetails> findById(Integer customerId);

	public CustomerDetails generateSactionId(Integer customerId, SanctionLetter santionletter);

}
