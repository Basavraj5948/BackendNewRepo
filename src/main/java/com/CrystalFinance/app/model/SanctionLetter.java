package com.CrystalFinance.app.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SanctionLetter {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer sanctionId;
	private String sanctionDate;
	private String applicantName;
	private String contactDetails;
	private Double loanAmountSanctioned;
	private Double rateOfInterest;
	private Integer loanTenure;
	private Double monthlyEmiAmount;
	private String termsAndCondition;
	private String sanctionStatus;
	@Lob
	private byte[] sanctionLetter;
}
