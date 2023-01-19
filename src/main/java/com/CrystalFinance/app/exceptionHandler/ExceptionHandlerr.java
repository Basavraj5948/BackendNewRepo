package com.CrystalFinance.app.exceptionHandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import 	org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.exception.EnquiryCanNotUpdateException;
import com.CrystalFinance.app.exception.EnquiryNotFound;
import com.CrystalFinance.app.repsonse.ApiErrorResponse;

@RestControllerAdvice
public class ExceptionHandlerr {

	 @ExceptionHandler(value=EnquiryNotFound.class)
		public ResponseEntity<ApiErrorResponse> handlerNotPresent(){
			ApiErrorResponse apir= new ApiErrorResponse(204,"Enquiry Not Found",new Date());
			return new ResponseEntity<ApiErrorResponse>(apir,HttpStatus.NOT_FOUND);
			
		}
	    
	    @ExceptionHandler(value=EnquiryCanNotUpdateException.class)
		public ResponseEntity<ApiErrorResponse> enquiryCanNotUpdate(){
			ApiErrorResponse apir= new ApiErrorResponse(204,"Enquiry Not Update",new Date());
			return new ResponseEntity<ApiErrorResponse>(apir,HttpStatus.NOT_FOUND);		
		}
	    
	    @ExceptionHandler(value=CustomerNotFound.class)
		public ResponseEntity<ApiErrorResponse> customerNotFound(){
			ApiErrorResponse apir= new ApiErrorResponse(204,"Customer Not Found",new Date());
			return new ResponseEntity<ApiErrorResponse>(apir,HttpStatus.NOT_FOUND);		
		}
}
