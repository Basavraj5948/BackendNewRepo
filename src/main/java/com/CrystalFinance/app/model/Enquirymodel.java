package com.CrystalFinance.app.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Enquirymodel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer enquiryId;
	private String customerFirstName;
	private String customerLastName;
	private String panCard;
	private Integer aadharNumber;
	private String emailId;
//	@Enumerated(EnumType.STRING)    //optional
	private String cibilStatus;
	private Integer cibilScore;
	private Double mobileNumber;
	

}
