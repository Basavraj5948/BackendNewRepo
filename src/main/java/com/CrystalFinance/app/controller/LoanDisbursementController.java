package com.CrystalFinance.app.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.CrystalFinance.app.enums.CustomerLoanStatus;
import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.repsonse.BaseResponse;
import com.CrystalFinance.app.service.LoanDisbursementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/LoanDisbursement")
@CrossOrigin("*")
public class LoanDisbursementController {
	@Autowired
	LoanDisbursementService ls;
	
	@PutMapping("/loanDisbursementupdate")
	public ResponseEntity<BaseResponse<CustomerDetails>> updateLoan(@RequestPart ("allData") String allData) throws IOException
	{ 
		ObjectMapper om= new ObjectMapper();
		CustomerDetails cst=om.readValue(allData, CustomerDetails.class);
		Optional<CustomerDetails> customer = ls.finddById(cst.getCustomerId());	
		if(customer.isEmpty()) {			
			throw new CustomerNotFound();
		}else {		
			cst.setCustomerLoanStatus(String.valueOf(CustomerLoanStatus.LoanDisbursed));
			CustomerDetails customerdetails=ls.updateloanDisbursement(cst);
			BaseResponse br= new BaseResponse<>(201,"Loan Disbursed",customerdetails);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(br,HttpStatus.ACCEPTED);
		}		
	}
}
