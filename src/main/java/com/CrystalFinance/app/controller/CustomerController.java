package com.CrystalFinance.app.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.CrystalFinance.app.CarLoanFinalProjectApplication;
import com.CrystalFinance.app.enums.CustomerLoanStatus;
import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.repsonse.BaseResponse;
import com.CrystalFinance.app.service.CustomerServiceI;
import com.CrystalFinance.app.service.SanctionService;
import com.CrystalFinance.app.serviceImpl.SanctionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerServiceI cs;
//	@Autowired
//	CustomerDetails customerdetails;
	
	@Autowired
	SanctionService scs;

	@PostMapping(value = "/postCustomer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)	//post all data with multipart file
	public ResponseEntity<BaseResponse<CustomerDetails>> postCustomer(@RequestPart("allData") String allData,
			@RequestPart("panCard") MultipartFile file1, @RequestPart("incomeProof") MultipartFile file2,
			@RequestPart("aadharCard") MultipartFile file3, @RequestPart("photo") MultipartFile file4,
			@RequestPart("signature") MultipartFile file5, @RequestPart("bankPassBook") MultipartFile file6) {

		// System.out.println(allData);
		BaseResponse bs = null;
		ObjectMapper om = new ObjectMapper();
		try {
			CustomerDetails cd1 = om.readValue(allData, CustomerDetails.class);
			
			cd1.getCustomerAllDocuments().setPanCard(file1.getBytes());
			cd1.getCustomerAllDocuments().setIncomeProof(file2.getBytes());
			cd1.getCustomerAllDocuments().setAadharCard(file3.getBytes());
			cd1.getCustomerAllDocuments().setPhoto(file4.getBytes());
			cd1.getCustomerAllDocuments().setSignature(file5.getBytes());
			cd1.getCustomerAllDocuments().setBankPassBook(file6.getBytes());
			cd1.setCustomerLoanStatus(String.valueOf(CustomerLoanStatus.pending));
			CustomerDetails saveCustomer = cs.saveCustomer(cd1);
			bs = new BaseResponse<>(201, "Data Saved", saveCustomer);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<BaseResponse<CustomerDetails>>(bs, HttpStatus.OK);
	}

	@GetMapping("/getCustomer/{custloanstatus}")	//get customer by loan status
	public ResponseEntity<BaseResponse<Iterable<CustomerDetails>>> getCustomerByStatus(
			@PathVariable("custloanstatus") String custloanstatus) {
		CustomerDetails cstd = null;
		Iterable<CustomerDetails> cst = cs.getCustomerbyStatus(custloanstatus);
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

	@GetMapping("/getcustomer")	// without status all customers
	public ResponseEntity<BaseResponse<CustomerDetails>> getCustomer() {

		Iterable<CustomerDetails> cus = cs.getCustomer();
		if (cus != null) {
			BaseResponse bs = new BaseResponse(200, "all data get", cus);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(bs, HttpStatus.OK);
		} else {
			throw new CustomerNotFound();
		}
	}

	@GetMapping("/getsingleCutomer/{enquid}") //get single customer by id
	public ResponseEntity<BaseResponse<CustomerDetails>> getSingleCustomer(@PathVariable Integer enquid) {
		Optional<CustomerDetails> singleCustomer = cs.findById(enquid);
		if (singleCustomer.isPresent()) {
			BaseResponse br = new BaseResponse<>(200, "Data Ok", singleCustomer);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);

		} else {
			throw new CustomerNotFound();
		}
	}

	@PutMapping(value = "/updateCustomer/{customerId}")	//update customer by loan status--
	public ResponseEntity<BaseResponse<CustomerDetails>> updateCustomer(@RequestBody String loanstatus,
			                                                            @PathVariable("customerId") Integer customerId) throws IOException
	{
	    
		Optional<CustomerDetails> customerdetails = cs.findById(customerId);
		
		if (customerdetails.isPresent())
		{
			CustomerDetails singlecustomer = customerdetails.get();	
			if(loanstatus.equals("documentverfied")) 
			{
				singlecustomer.setCustomerLoanStatus(String.valueOf(CustomerLoanStatus.DocumentVerified));
				CustomerDetails cd = cs.updateCustomer(singlecustomer);
				BaseResponse br = new BaseResponse<>(200, "Data Successfully Updated Approved Customer", cd);
				return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);
			}
			else if(loanstatus.equals("documentrejected"))
			{
				singlecustomer.setCustomerLoanStatus(String.valueOf(CustomerLoanStatus.DocumentRejected));
				CustomerDetails cd2 = cs.updateCustomer(singlecustomer);
				BaseResponse br = new BaseResponse<>(200, "Data Successfully Updated for Rejected Customer", cd2);
				return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);	
			}
		}
		else 
		{
			throw new CustomerNotFound();
		}
		BaseResponse br = new BaseResponse<>(404, "Failed to Update Data..", null);
		return new ResponseEntity<BaseResponse<CustomerDetails>>(br,HttpStatus.NOT_FOUND);
	
	}

	@DeleteMapping("/deleteCustomer/{csid}")	//delete customer by id
	public ResponseEntity<BaseResponse<CustomerDetails>> deleteCustomer(@PathVariable Integer csid) {

		Optional<CustomerDetails> customerdetail = cs.findById(csid);
		if (customerdetail.isPresent()) {
			cs.deleteData(csid);
			BaseResponse br = new BaseResponse<>(200, "Data Deleted Successfully", customerdetail);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);

		} else {

			throw new CustomerNotFound();
		}
	}
	
	@PutMapping("/createSanctionLetter")
	public void createSanctionLetter(@RequestBody CustomerDetails customerdetail)
	{
		scs.generatePdfReport(customerdetail);

	}
}
