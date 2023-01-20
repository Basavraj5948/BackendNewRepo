package com.CrystalFinance.app.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CrystalFinance.app.enums.CustomerLoanStatus;
import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.SanctionLetter;
import com.CrystalFinance.app.repsonse.BaseResponse;
import com.CrystalFinance.app.service.SanctionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin("*")
@RestController
@RequestMapping("/sanction")
public class SanctionController {
	
	@Autowired
	SanctionService ss;
	
	@GetMapping("/getCustomer/{custloanstatus}")	//get customer by loan status
	public ResponseEntity<BaseResponse<Iterable<CustomerDetails>>> getCustomerByStatus(
			@PathVariable("custloanstatus") String custloanstatus) {
		CustomerDetails cstd = null;
		Iterable<CustomerDetails> cst = ss.getCustomerbyStatus(custloanstatus);
		for (CustomerDetails cstds : cst) {
			if (cstds != null) {
				cstd = cstds;
			}
		}
		if (cstd != null) {
			BaseResponse<Iterable<CustomerDetails>> br = new BaseResponse<>(200, "All Data Successfully get..", cst);
			return new ResponseEntity<BaseResponse<Iterable<CustomerDetails>>>(br, HttpStatus.OK);
		} else {
			throw new CustomerNotFound();
		}
	}


	@PutMapping("/generatePdf/{customerId}")
	public ResponseEntity<BaseResponse<CustomerDetails>> updateSanctionLetter(@PathVariable("customerId") Integer customerId, 
			                                                                  @RequestBody SanctionLetter santionletter)
	{
	
		    System.out.println(santionletter);
			CustomerDetails customerDetails = ss.generateSactionId(customerId,santionletter);
			BaseResponse br = new BaseResponse<>(200, "Data Successfully Updated..", customerDetails);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);
		
	}
	
}