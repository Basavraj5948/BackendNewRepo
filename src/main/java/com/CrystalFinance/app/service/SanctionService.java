package com.CrystalFinance.app.service;

import com.CrystalFinance.app.exception.PdfNotGenerated;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.Email;
import com.CrystalFinance.app.model.SanctionLetter;

public interface SanctionService {

	public Iterable<CustomerDetails> getCustomerbyStatus(String custloanstatus);

	public CustomerDetails updateCustomer(Integer cstid, CustomerDetails csd);

	public CustomerDetails generateSactionId(Integer customerid, SanctionLetter sanctionLetter) throws PdfNotGenerated;

}
