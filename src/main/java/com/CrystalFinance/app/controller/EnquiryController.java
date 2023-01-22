package com.CrystalFinance.app.controller;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrystalFinance.app.enums.CibilStatus;
import com.CrystalFinance.app.exception.EnquiryNotFound;
import com.CrystalFinance.app.model.Email;
import com.CrystalFinance.app.model.Enquirymodel;
import com.CrystalFinance.app.repsonse.BaseResponse;
import com.CrystalFinance.app.service.EmailService;
import com.CrystalFinance.app.service.EnquiryserviceI;

@RestController
@CrossOrigin("*")
@RequestMapping("/Enquiry")
public class EnquiryController {
	@Autowired
	EnquiryserviceI es;
	@Autowired
    EmailService emailservice;
	@Autowired
	Email email;
	
	@PostMapping("/postEnquiry")
	public ResponseEntity<BaseResponse<Enquirymodel>> postEnquiry(@RequestBody Enquirymodel m) {

		m.setCibilStatus(String.valueOf(CibilStatus.pending));

		Enquirymodel enquiry= es.saveEnquiry(m);
		BaseResponse bs=new BaseResponse<>(201,"Data saved",enquiry);
		
		return new ResponseEntity<BaseResponse<Enquirymodel>>(bs,HttpStatus.OK);
		
	}
	@GetMapping("/getEnquiry/{CIBILStatus}")
	public ResponseEntity<BaseResponse<Iterable<Enquirymodel>>> getEnquiry(@PathVariable("CIBILStatus") String CIBILStatus)
	{
		Enquirymodel eq=null;
		
		 Iterable<Enquirymodel> enq = es.getEnquiry(CIBILStatus);
		 for(Enquirymodel enq1:enq) {
			 if(enq1 !=null) {
				 eq=enq1;
			 }
		 }
		 if(eq !=null) {
		 BaseResponse<Iterable<Enquirymodel>> ba= new BaseResponse<>(200,"All data Ok",enq);
		 return new ResponseEntity<BaseResponse<Iterable<Enquirymodel>>>(ba,HttpStatus.OK) ;
		 }else {
		 throw new EnquiryNotFound();
		 }		
	}
	
	@GetMapping("/getsingleenquiry/{enqid}")
	public ResponseEntity<BaseResponse<Enquirymodel>> getSingleEnquiry(@PathVariable Integer enqid){
		Optional<Enquirymodel> singleEnquiry = es.getSingleEnquiry(enqid);
		if(singleEnquiry.isEmpty()) {
			throw new EnquiryNotFound();
		}else {
			BaseResponse ba= new BaseResponse<>(200,"All data Ok",singleEnquiry);
			return new ResponseEntity<BaseResponse<Enquirymodel>>(ba,HttpStatus.OK);
		}
	}
	
	
	@PutMapping("/CheckCIBIL/{enquiryId}")
	public ResponseEntity<BaseResponse<Enquirymodel>> checkCibilScore(@PathVariable("enquiryId") Integer enquiryId,@RequestBody Enquirymodel enq)
	{
		Random ramdom=new Random();
		int cibilScore = ramdom.nextInt(300, 900);
		
		if(cibilScore>=600 && cibilScore<=900) 
		{
			enq.setCibilStatus(String.valueOf(CibilStatus.approved));
			enq.setCibilScore(cibilScore);
		
		    Enquirymodel enquiry = es.saveEnquiry(enq);

		    BaseResponse<Enquirymodel> baseResponse = new BaseResponse<Enquirymodel>(200,"CIBIL Approved",enquiry);
			return new ResponseEntity<BaseResponse<Enquirymodel>>(baseResponse,HttpStatus.OK);
		}
		else
		{
			enq.setCibilStatus(String.valueOf(CibilStatus.rejected));
			enq.setCibilScore(cibilScore);
			
			Enquirymodel enquiry = es.saveEnquiry(enq);
			
//			email.setFromEmail(fromEmail);
//	        email.setToEmail(enq.getEmailId());
//			email.setSubject("Regarding Car Loan For Documentation of Applicant name: "+ enq.getCustomerFirstName() +" "+ enq.getCustomerLastName());
//			email.setText("your cibil is Rejected and You are Not Eligible\n"
//	        		+ "For Further Process."
//	        		+ "\n We are Not Happy to inform you that your Home Loan request has been Rejected and is currently being Not Processed.\n"
////	        		+ "Further, we inform you that we have sent an email containing attached documents.\n"
////	        		+ "Also we have sent you the terms and conditions of the loans sanctioned. \n"
////	        		+ "We would like to schedule your meeting with the finance officer of the company for any further information and clarifications that you might wish to know. \n\n"
////	        		+ "We are happy to be doing business with you. \n\n"
////	        		+ "List of Documents Required :- \n\n"
////	        		+ "1.Aadhar Card \n"
////	        		+ "2.Pan Card \n"
////	        		+ "3.Income Tax Return of Last Two Years \n"
////	        		+ "4.Salary Slips of Last Three Months \n"
////	        		+ "5.Passport Size Photograph \n"
////	        		+ "6.Bank Passbook Copy \n"
//	        		+ "\n \n Thanking You. \n"
//	        		+ "Mr.Basavraj Bhorshetti \n"
//	        		+ "Branch Manager \n"
//	        		+ "Crystal Finance Ltd. \n Karvenagar \n"
//	        		+ "Pune, Maharashtra \n India-411052\n"
//	        		+ "\n"
//	        		+ "Thank You For Banking With Us \n\n"
//	        		+ "Crystal Finance Ltd.....!!!!");
//	        emailservice.sendMail(email);
	        BaseResponse<Enquirymodel> baseResponse = new BaseResponse<Enquirymodel>(200,"CIBIL Rejected",enquiry);
			return new ResponseEntity<BaseResponse<Enquirymodel>>(baseResponse,HttpStatus.OK);
		}
		
		
	}
}
