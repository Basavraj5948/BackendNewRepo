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
public class CustomerProfession {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer professionId;
	private String professionType;
	private Double professionMonthlyIncome;
	private String professionDesignation;
	

}

