package com.CrystalFinance.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.Email;
import com.CrystalFinance.app.model.Enquirymodel;
import com.CrystalFinance.app.model.SanctionLetter;
import com.CrystalFinance.app.repsonse.BaseResponse;
import com.CrystalFinance.app.service.CustomerServiceI;
import com.CrystalFinance.app.service.EmailService;

@RestController
@CrossOrigin("*")
@RequestMapping("/mail")
public class EmailController 
{
 
	@Autowired
	Email email;
	
	@Autowired
	EmailService emailservice;
	
	@Autowired
	CustomerServiceI cs;
	
	@Value("${spring.mail.username}")
	private String fromEmail;
	
	@PostMapping("/sendmail")
	public ResponseEntity<BaseResponse<Enquirymodel>> sendMail(@RequestBody Enquirymodel enq)
	{
		System.out.println("cibil status "+enq.getCibilStatus());
		if(enq.getCibilStatus().equals("approved"))
		{
		email.setFromEmail(fromEmail);
        email.setToEmail(enq.getEmailId());
		email.setSubject("Regarding Car Loan For Documentation of Applicant name: "+ enq.getCustomerFirstName() +" "+ enq.getCustomerLastName());
		email.setText("your cibil is Approved and You are Eligible\n"
      		+ "For Further Process."
      		+ "\n We are happy to inform you that your Home Loan request has been approved and is cureently being processed.\n"
      		+ "Further, we inform you that we have sent an email containing attached documents.\n"
      		+ "Also we have sent you the terms and conditions of the loans sanctioned. \n"
      		+ "We would like to schedule your meeting with the finance officer of the company for any further information and clarifications that you might wish to know. \n\n"
      		+ "We are happy to be doing business with you. \n\n"
      		+ "List of Documents Required :- \n\n"
      		+ "1.Aadhar Card \n"
      		+ "2.Pan Card \n"
      		+ "3.Income Tax Return of Last Two Years \n"
      		+ "4.Salary Slips of Last Three Months \n"
      		+ "5.Passport Size Photograph \n"
      		+ "6.Bank Passbook Copy \n"
      		+ "\n \n Thanking You. \n"
      		+ "Mr.Basavraj Bhorshetti\n"
      		+ "Branch Manager \n"
      		+ "Crystal Finance Ltd. \n Karvenagar \n"
      		+ "Pune, Maharashtra \n India-411052\n"
      		+ "\n"
      		+ "Thank You For Banking With Us \n\n"
      		+ "Crystal Finance Ltd.....!!!!");
        emailservice.sendMail(email);
        BaseResponse<Enquirymodel> baseResponse = new BaseResponse<Enquirymodel>(200,"Mail Send Successfully for Approved Customer",enq);
        return new ResponseEntity<BaseResponse<Enquirymodel>>(baseResponse,HttpStatus.OK);
		}
		else 
		{
			email.setFromEmail(fromEmail);
	        email.setToEmail(enq.getEmailId());
			email.setSubject("Regarding Car Loan For Documentation of Applicant name: "+ enq.getCustomerFirstName() +" "+ enq.getCustomerLastName());
			email.setText("your cibil is Rejected and You are Not Eligible\n"
	        		+ "For Further Process."
	        		+ "\n We are Not Happy to inform you that your Home Loan request has been Rejected and is currently being Not Processed.\n"
//	        		+ "Further, we inform you that we have sent an email containing attached documents.\n"
//	        		+ "Also we have sent you the terms and conditions of the loans sanctioned. \n"
//	        		+ "We would like to schedule your meeting with the finance officer of the company for any further information and clarifications that you might wish to know. \n\n"
//	        		+ "We are happy to be doing business with you. \n\n"
//	        		+ "List of Documents Required :- \n\n"
//	        		+ "1.Aadhar Card \n"
//	        		+ "2.Pan Card \n"
//	        		+ "3.Income Tax Return of Last Two Years \n"
//	        		+ "4.Salary Slips of Last Three Months \n"
//	        		+ "5.Passport Size Photograph \n"
//	        		+ "6.Bank Passbook Copy \n"
	        		+ "\n \n Thanking You. \n"
	        		+ "Mr.Basavraj Bhorshetti \n"
	        		+ "Branch Manager \n"
	        		+ "Crystal Finance Ltd. \n Karvenagar \n"
	        		+ "Pune, Maharashtra \n India-411052\n"
	        		+ "\n"
	        		+ "Thank You For Banking With Us \n\n"
	        		+ "Crystal Finance Ltd.....!!!!");
	        emailservice.sendMail(email);
	        BaseResponse<Enquirymodel> baseResponse = new BaseResponse<Enquirymodel>(200,"Mail Send successfully for Rejected Customer",enq);
	        return new ResponseEntity<BaseResponse<Enquirymodel>>(baseResponse,HttpStatus.OK);
		}
	
	}
	
	@GetMapping("/sendSantionLetterMail/{customerId}")
	public ResponseEntity<BaseResponse<CustomerDetails>> sendSanctionLetterMail(@PathVariable("customerId") Integer customerId)
	{
		System.out.println("Mail sending started");
		Optional<CustomerDetails> customerdetail = cs.findById(customerId);
		CustomerDetails customerDetails = customerdetail.get();
		if(customerdetail.isPresent())
		{
			emailservice.sendSantionLetterMail(customerDetails);
		}
		else
		{
			throw new CustomerNotFound();
		}
		BaseResponse<CustomerDetails> baseResponse = new BaseResponse<CustomerDetails>(200,"Mail Send successfully!!!!",customerDetails);
		return new ResponseEntity<BaseResponse<CustomerDetails>>(baseResponse,HttpStatus.OK);
	}
	
}
