package com.CrystalFinance.app.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.SanctionLetter;
import com.CrystalFinance.app.repository.CustomerRepository;
import com.CrystalFinance.app.service.SanctionService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class SanctionServiceImpl implements SanctionService {

	@Autowired
	CustomerRepository cr;
	
	@Autowired
	JavaMailSender javaMailSender;
	
	Logger logger=LoggerFactory.getLogger(SanctionServiceImpl.class);
	
	@Override
	public Iterable<CustomerDetails> getCustomerbyStatus(String custloanstatus) {
		Iterable<CustomerDetails> get = cr.findAllByCustomerLoanStatus(custloanstatus);
		return get;
	}

	@Override
	public CustomerDetails updateCustomer(Integer cstid, CustomerDetails csd) {
		Optional<CustomerDetails> findCustomerById = cr.findById(cstid);
		csd.setCustomerId(cstid);
		if (findCustomerById.isPresent()) {
			CustomerDetails save = cr.save(csd);
			return save;
		} else {
			throw new CustomerNotFound();
		}
	}




	@Override
	public Optional<CustomerDetails> findById(Integer customerId)
	{
		return cr.findById(customerId);
	}

	@Override
	public CustomerDetails generateSactionId(Integer customerId, SanctionLetter santionletter)
	{
		 Optional<CustomerDetails> customerdetails = cr.findById(customerId);  
			CustomerDetails customerdetails1=customerdetails.get();
			
//			customerdetails1.setCustomerId(customerdetails1.getCustomerId());
//		    customerdetails1.getCustomerSanctionLetter().setSanctionId(santionletter.getSanctionId());
			customerdetails1.getCustomerSanctionLetter().setSanctionDate(santionletter.getSanctionDate());
			customerdetails1.getCustomerSanctionLetter().setApplicantName(santionletter.getApplicantName());
			customerdetails1.getCustomerSanctionLetter().setContactDetails(santionletter.getContactDetails());
			customerdetails1.getCustomerSanctionLetter().setLoanAmountSanctioned(santionletter.getLoanAmountSanctioned());
			customerdetails1.getCustomerSanctionLetter().setRateOfInterest(santionletter.getRateOfInterest());
			customerdetails1.getCustomerSanctionLetter().setLoanTenure(santionletter.getLoanTenure());
			customerdetails1.getCustomerSanctionLetter().setMonthlyEmiAmount(santionletter.getMonthlyEmiAmount());
			customerdetails1.getCustomerSanctionLetter().setTermsAndCondition(santionletter.getTermsAndCondition());
			customerdetails1.getCustomerSanctionLetter().setSanctionStatus(santionletter.getSanctionStatus());
			/*
			 * customerDetails.getCustomerSanctionLetter().setSanctionId(customerdetails1.
			 * getCustomerSanctionLetter().getSanctionId());
			 * customerDetails.getCustomerVehicleInformation().setCustomerVehicleId(
			 * customerdetails1.getCustomerVehicleInformation().getCustomerVehicleId());
			 * customerDetails.getCustomerProfession().setProfessionId(customerdetails1.
			 * getCustomerProfession().getProfessionId());
			 * customerDetails.getCustomerLoanDisbursement().setAgreementId(customerdetails1
			 * .getCustomerLoanDisbursement().getAgreementId());
			 * customerDetails.getCustomerBankDetails().setCustomerBankDetailsId(
			 * customerdetails1.getCustomerBankDetails().getCustomerBankDetailsId());
			 * customerDetails.getCustomerAllDocuments().setDocumentId(customerdetails1.
			 * getCustomerAllDocuments().getDocumentId());
			 * customerDetails.getCustomerAddress().setAddressId(customerdetails1.
			 * getCustomerAddress().getAddressId());
			 * customerDetails.getCustomerDealer().setDealerId(customerdetails1.
			 * getCustomerDealer().getDealerId());
			 * customerDetails.getCustomerLedger().setLedgerId(customerdetails1.
			 * getCustomerLedger().getLedgerId());
			 */	
			logger.info("create Pdf Started");
			String title="Crystal Finance";
//			String content="WelCome To Crystal Finance Mr."+customerDetails.getCustomerSanctionLetter().getApplicantName()+"You are FullFill Our Terms and Condition For your Loan\n"
//					+ "thats why we are cosindering you for loan Your Loan Details are as follows\n"
//					+ "So you loan saction ID is"+customerDetails.getCustomerSanctionLetter().getSanctionId()+"your Sactioned Loan Ammount Is."+customerDetails.getCustomerSanctionLetter().getLoanAmountSanctioned()+"\n"
//					+ "On interest of."+customerDetails.getCustomerSanctionLetter().getRateOfInterest()+"Tenure of."+customerDetails.getCustomerSanctionLetter().getLoanTenure()+"\n"
//							+ "Emi about."+customerDetails.getCustomerSanctionLetter().getMonthlyEmiAmount()+"Thats all Let me Know Your Responce Through Our SalesExecutive \n"
//									+ "Thanku!";
			
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			Document  document=new Document();

			
			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font.setColor(CMYKColor.BLUE);
			

			Font font1 = FontFactory.getFont(FontFactory.TIMES);
			font1.setColor(25, 15, 30);
			
			PdfPTable table = new PdfPTable(1);
			table.setWidthPercentage(100f);
			table.setWidths(new int[] {2});
			table.setSpacingBefore(10);
			
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.LIGHT_GRAY);
			cell.setPadding(5);
			
			cell.setPhrase(new Phrase("Sactioned Ammount Is   :"+customerdetails1.getCustomerSanctionLetter().getLoanAmountSanctioned(), font));
			table.addCell(cell);

			
			cell.setPhrase(new Phrase("Rate Of Interest Is   :"+customerdetails1.getCustomerSanctionLetter().getRateOfInterest(), font));
			table.addCell(cell);

			
			cell.setPhrase(new Phrase("Monthly Emi   :"+customerdetails1.getCustomerSanctionLetter().getMonthlyEmiAmount(), font));
			table.addCell(cell);

			
			cell.setPhrase(new Phrase("Tenure (In years)   :"+customerdetails1.getCustomerSanctionLetter().getLoanTenure(), font));
			table.addCell(cell);

			
			PdfWriter.getInstance(document, out);
			document.open();
			
			Font fontTitle=FontFactory.getFont(FontFactory.HELVETICA_BOLD,26);
			Paragraph paragraph=new Paragraph(title,fontTitle);
			paragraph.setAlignment(Element.ALIGN_CENTER);
			document.add(paragraph);
					
			document.add(table);
			
			document.close();
			
			ByteArrayInputStream arrayInputStream=new ByteArrayInputStream(out.toByteArray());
			
			MimeMessage message=javaMailSender.createMimeMessage();
			try {
				MimeMessageHelper helper=new MimeMessageHelper(message,true);
				helper.setFrom("basavarajbhorshetti315@gmail.com");
				helper.setTo("manjresakshi98@gmail.com");
				helper.setSubject("CrystalCarLoan");
				helper.setText("SactionLetter");
				
				byte[] readAllBytes = arrayInputStream.readAllBytes();
				
				customerdetails1.getCustomerSanctionLetter().setSanctionLetter(readAllBytes);	
				
				helper.addAttachment("sactioneletter.pdf", new ByteArrayResource(readAllBytes));
				
				javaMailSender.send(message);
			} catch (Exception e) {
				// TODO: handle exception
			}
		return cr.save(customerdetails1);
	} 

}