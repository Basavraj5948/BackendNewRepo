package com.CrystalFinance.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerBankDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerBankDetailsId;
	private Long customerBankAccountNumber;
	private String customerBankName;
	private String customerBankBranchName;
	private String customerBankIfscNumber; // change  to string
}
