package com.CrystalFinance.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Defaulter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer defaulterId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmailId;
}
