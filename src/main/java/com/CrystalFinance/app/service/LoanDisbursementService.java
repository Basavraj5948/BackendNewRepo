package com.CrystalFinance.app.service;

import java.util.Optional;

import com.CrystalFinance.app.model.CustomerDetails;

public interface LoanDisbursementService {

	public CustomerDetails updateloanDisbursement( CustomerDetails cst);

	public Optional<CustomerDetails> finddById(Integer cstid);

}
