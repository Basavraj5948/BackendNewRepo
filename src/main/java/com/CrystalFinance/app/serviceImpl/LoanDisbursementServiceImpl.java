package com.CrystalFinance.app.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.repository.CustomerRepository;
import com.CrystalFinance.app.service.LoanDisbursementService;
@Service
public class LoanDisbursementServiceImpl implements LoanDisbursementService {
	
	@Autowired
	CustomerRepository cr;
	
	@Override
	public CustomerDetails updateloanDisbursement( CustomerDetails cst)
	{

		return null;
		
	}
	
	@Override
	public Optional<CustomerDetails> finddById(Integer cstid) 
	{
//		  Optional<CustomerDetails> data = cr.findById(cstid);
//		  System.out.println(data);
		 Optional<CustomerDetails> data = cr.findById(cstid);
		 System.out.println(data);
		  return data;
		
	}

}
