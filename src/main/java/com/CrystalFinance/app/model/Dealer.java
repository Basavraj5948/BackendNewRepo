package com.CrystalFinance.app.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Dealer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dealerId;
	private String dealerName;
	private String dealerAddress;
	private Long dealerBankAccountNumber;
	private String dealerBankName;
	private String dealerBankBranchName;
	private String dealerBankIfscNumber;

//	@OneToOne(cascade = CascadeType.ALL)
//	private DealerBankDetails dealerBankDetails;
//	

}