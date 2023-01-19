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
public class AllDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer documentId;
	@Lob
	private byte[] panCard;
	@Lob 
	private byte[] incomeProof;
	@Lob 
	private byte[] aadharCard;
	@Lob 
	private byte[] photo;
	@Lob 
	private byte[] signature;
	@Lob 
	private byte[] bankPassBook;
	
	
	
	

}
