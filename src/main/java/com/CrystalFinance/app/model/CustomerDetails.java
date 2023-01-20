package com.CrystalFinance.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
public class CustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerId;
	private String customerFirstName;
	private String customerMiddleName;
	private String customerLastName;
	private Long customerMobileNumber;
	private Long customerAdditionalMobileNumber;
	private String customerPanCard;
	private Long customerAadharCard;	
	private String customerDateOfBirth;
	private String customerEmail;
	private String customerGender;
	private String customerQualification;
	private Integer customerCibilScore;
	private String customerLoanStatus;
	private Double customerLoanAmountRequired;
	
	@OneToOne(cascade = CascadeType.ALL)
	private AllDocuments customerAllDocuments;

	@OneToOne(cascade = CascadeType.ALL)
	private CustomerAddress customerAddress;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerProfession customerProfession;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Dealer customerDealer;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerVehicleInformation customerVehicleInformation;
	
	@OneToOne(cascade = CascadeType.ALL)
	private CustomerBankDetails customerBankDetails;
	
	@OneToOne(cascade = CascadeType.ALL)
	private LoanDisbursement customerLoanDisbursement;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Ledger customerLedger;
	
	@OneToOne(cascade = CascadeType.ALL)
	private SanctionLetter customerSanctionLetter;

}
