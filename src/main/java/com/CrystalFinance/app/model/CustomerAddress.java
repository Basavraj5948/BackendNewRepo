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
public class CustomerAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer addressId;// customerAddressId;
	private String localAreaName;
	private String localCityName;
	private String localDistrict;
	private String localState;
	private Long localPincode;
	private String localHouseNumber;
	private String localStreetName;
	private String permanentAreaName;
	private String permanentCityName;
	private String permanentDistrict;
	private String permanentState;
	private Long permanentPincode;
	private String permanentHouseNumber;
	private String permanentStreetName;
	
//
//    addressId:number;
//    localAreaName:string;
//    localCityName:string;
//    localDistrict:string;
//    localState:string;
//    localPincode:number;
//    localHouseNumber:number;
//    localStreetName:string;
//    permanentAreaName:string;    
//    permanentCityName:string;
//    permanentDistrict:string;
//    permanentState:string;
//    permanentPincode:number;
//    permanentHouseNumber:number;
//    permanentStreetName:string;

}
