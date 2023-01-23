package com.CrystalFinance.app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.repository.CustomerRepository;
import com.CrystalFinance.app.service.LedgerService;

@Service
public class LedgerServiceImpl implements LedgerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Optional<CustomerDetails> findById(Integer cusid) {
		Optional<CustomerDetails> findById = customerRepository.findById(cusid);
		return findById;
	}

	@Override
	public CustomerDetails saveCustomerLedger(CustomerDetails customerDetails) {
		CustomerDetails savedCustomerDetails = customerRepository.save(customerDetails);
		return savedCustomerDetails;
	}

}
