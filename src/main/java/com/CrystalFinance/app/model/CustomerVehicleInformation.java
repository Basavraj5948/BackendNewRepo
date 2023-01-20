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
public class CustomerVehicleInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer customerVehicleId;
	private String customerVehicleModel;
	private String customerVehicleChassisNumber;
	private Double customerVehiclePrice;
//	private String customervehicleNumber;
//	private String customerVehicleRcNumber; 

}
