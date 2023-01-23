package com.CrystalFinance.app.service;

import java.util.Optional;

import com.CrystalFinance.app.model.CustomerDetails;

public interface LedgerService {

public	Optional<CustomerDetails> findById(Integer cusid);

public CustomerDetails saveCustomerLedger(CustomerDetails customerDetails);

}
