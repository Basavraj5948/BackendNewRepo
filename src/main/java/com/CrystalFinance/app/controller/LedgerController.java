package com.CrystalFinance.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.Ledger;
import com.CrystalFinance.app.repsonse.BaseResponse;
import com.CrystalFinance.app.service.LedgerService;

@CrossOrigin("*")
@RequestMapping("/ledger")
@RestController
public class LedgerController {

	@Autowired
	LedgerService ledgerService;
	
	@PutMapping("/updateLedger/{cusid}")
	public ResponseEntity<BaseResponse<CustomerDetails>> updateLedger(@PathVariable("cusid") Integer cusid,@RequestBody Ledger ledger){
		Optional<CustomerDetails> findById = ledgerService.findById(cusid);
		CustomerDetails customerDetails = findById.get();
		if(findById.isPresent()) {
			customerDetails.getCustomerLedger().setLedgerId(ledger.getLedgerId());
			customerDetails.getCustomerLedger().setTotalPrincipalAmount(ledger.getTotalPrincipalAmount());
			customerDetails.getCustomerLedger().setMonthlyEMI(ledger.getMonthlyEMI());
			customerDetails.getCustomerLedger().setTenure(ledger.getTenure());
			customerDetails.getCustomerLedger().setLedgerCreatedDate(ledger.getLedgerCreatedDate());
			customerDetails.getCustomerLedger().setNextEmiStartDate(ledger.getNextEmiStartDate());
			customerDetails.getCustomerLedger().setNextEmiEndDate(ledger.getNextEmiEndDate());
			customerDetails.getCustomerLedger().setPreviousEmiStatus(ledger.getPreviousEmiStatus());
			customerDetails.getCustomerLedger().setCurrentMonthEmiStatus(ledger.getCurrentMonthEmiStatus());
			customerDetails.getCustomerLedger().setLoanStatus(ledger.getLoanStatus());
			customerDetails.getCustomerLedger().setRemainingAmount(ledger.getRemainingAmount());
			customerDetails.getCustomerLedger().setAmountPaidtillDate(ledger.getAmountPaidtillDate());
			customerDetails.getCustomerLedger().setPayableAmountwithInterest(ledger.getPayableAmountwithInterest());
			customerDetails.getCustomerLedger().setLoanEndDate(ledger.getLoanEndDate());
		}else {
			throw new CustomerNotFound();
		}
		CustomerDetails saveCustomerLedger = ledgerService.saveCustomerLedger(customerDetails);
		BaseResponse baseResponse=new BaseResponse(200,"Ledger Info Updated",saveCustomerLedger);
		return new ResponseEntity<BaseResponse<CustomerDetails>>(baseResponse,HttpStatus.OK);
	}
}
