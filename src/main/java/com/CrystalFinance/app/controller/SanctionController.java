package com.CrystalFinance.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.CrystalFinance.app.exception.PdfNotGenerated;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.Email;
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
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
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

	
//	@PutMapping(value = "/updateCustomer/{cstid}")	//update customer by id
//	public ResponseEntity<BaseResponse<CustomerDetails>> updateCustomer(@PathVariable Integer cstid,
//			@RequestPart("allData") String allData) throws IOException {
//		ObjectMapper om = new ObjectMapper();
//		if(allData.isEmpty()) {
//			throw new CustomerNotFound();
//		}else {
//			CustomerDetails csd = om.readValue(allData, CustomerDetails.class);
//
//			csd.setCustomerLoanStatus(String.valueOf(CustomerLoanStatus.SanctionLetterGenerated));
//			CustomerDetails customerdetails = ss.updateCustomer(cstid, csd);
//			BaseResponse br = new BaseResponse<>(201, "Data Successfully Updated..", customerdetails);
//			return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.ACCEPTED);
//		}
//	}
	
	@PutMapping("/generatePdf/{customerid}")
	public ResponseEntity<BaseResponse<CustomerDetails>> updateSactionLetter(@PathVariable("customerid") Integer customerid, @RequestBody SanctionLetter sanctionLetter)throws PdfNotGenerated {
		Email email = new Email();
		CustomerDetails customerdetail = new CustomerDetails();
			email.setFromEmail(fromEmail);
			CustomerDetails customerdetails = ss.generateSactionId(customerid, sanctionLetter, email);
			customerdetail.setCustomerLoanStatus(String.valueOf(CustomerLoanStatus.SanctionLetterGenerated));
			BaseResponse br = new BaseResponse<>(200,"Sanction Letter Generated", customerdetails);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);

	}
	
}