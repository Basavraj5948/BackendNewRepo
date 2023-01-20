package com.CrystalFinance.app.serviceImpl;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.CrystalFinance.app.enums.CustomerLoanStatus;
import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.exception.PdfNotGenerated;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.Email;
import com.CrystalFinance.app.model.SanctionLetter;
import com.CrystalFinance.app.repository.CustomerRepository;
import com.CrystalFinance.app.service.SanctionService;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfImage;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class SanctionServiceImpl implements SanctionService {

	@Autowired
	CustomerRepository cr;

	@Autowired
	JavaMailSender sender;

	@Value("${spring.mail.username}")
	private String fromEmail;

	Logger logger = LoggerFactory.getLogger(SanctionServiceImpl.class);

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

//	@SuppressWarnings("deprecation")
	@Override
	public CustomerDetails generateSactionId(Integer customerID, SanctionLetter sanctionLetter ,Email email) throws PdfNotGenerated {
		Optional<CustomerDetails> customerdetails = cr.findById(customerID);
		CustomerDetails customerdetails1 = customerdetails.get();
		if(customerdetails.isPresent()) {
			customerdetails1.getCustomerSanctionLetter().setSanctionDate(sanctionLetter.getSanctionDate());
			customerdetails1.getCustomerSanctionLetter().setApplicantName(sanctionLetter.getApplicantName());
			customerdetails1.getCustomerSanctionLetter().setLoanAmountSanctioned(sanctionLetter.getLoanAmountSanctioned());
			customerdetails1.getCustomerSanctionLetter().setRateOfInterest(sanctionLetter.getRateOfInterest());
			customerdetails1.getCustomerSanctionLetter().setLoanTenure(sanctionLetter.getLoanTenure());
			customerdetails1.getCustomerSanctionLetter().setMonthlyEmiAmount(sanctionLetter.getMonthlyEmiAmount());
			customerdetails1.getCustomerSanctionLetter().setTermsAndCondition(sanctionLetter.getTermsAndCondition());

			logger.info("Loan Disbursement PDF started");
			String title = "Crystal Finance Ltd.";

			Document document = new Document(PageSize.A4);

			String content1 = "\n\n Dear " + customerdetails1.getCustomerLastName() + customerdetails1.getCustomerFirstName() + customerdetails1.getCustomerMiddleName()
					+ ","
					+ "\nCrystal Finance Ltd. is Happy to informed you that your loan application has been approved. ";

			String content2 = "\n\nWe hope that you find the terms and conditions of this loan satisfactory "
					+ "and that it will help you meet your financial needs.\n\nIf you have any questions or need any assistance regarding your loan, "
					+ "please do not hesitate to contact us.\n\nWe wish you all the best and thank you for choosing us."
					+ "\n\nSincerely,\n\n" + "James Smith (Credit manager)";

			ByteArrayOutputStream opt = new ByteArrayOutputStream();

			PdfWriter.getInstance(document, opt);
			document.open();

			Image img = null;
			try {
				img = Image.getInstance("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNLKhJsj6EMH4GV8VdiUqbKRby2e4zgBCI2g&usqp=CAU");
				img.scalePercent(50, 50);
				img.setAlignment(Element.ALIGN_RIGHT);
				document.add(img);

			} catch (BadElementException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Font titlefont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25);
			Paragraph titlepara = new Paragraph(title, titlefont);
			titlepara.setAlignment(Element.ALIGN_CENTER);
			document.add(titlepara);

			Font titlefont2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
			Paragraph paracontent1 = new Paragraph(content1, titlefont2);
			document.add(paracontent1);

			PdfPTable table = new PdfPTable(2);
			table.setWidthPercentage(100f);
			table.setWidths(new int[] { 2, 2 });
			table.setSpacingBefore(10);

			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(CMYKColor.WHITE);
			cell.setPadding(5);

			Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
			font.setColor(5, 5, 161);

			Font font1 = FontFactory.getFont(FontFactory.HELVETICA);
			font.setColor(5, 5, 161);

			cell.setPhrase(new Phrase("Loan amount Sanctioned", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.valueOf("₹ " + customerdetails1.getCustomerSanctionLetter().getLoanAmountSanctioned()),
					font1));
			table.addCell(cell);

			cell.setPhrase(new Phrase("loan tenure", font));
			table.addCell(cell);

			cell.setPhrase(new Phrase(String.valueOf(customerdetails1.getCustomerSanctionLetter().getLoanTenure()), font1));
			table.addCell(cell);

			cell.setPhrase(new Phrase("interest rate", font));
			table.addCell(cell);

			cell.setPhrase(
					new Phrase(String.valueOf(customerdetails1.getCustomerSanctionLetter().getRateOfInterest()) + " %", font1));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Sanction letter generated Date", font));
			table.addCell(cell);

			Date date = new Date();
			String curdate = date.toString();
			customerdetails1.getCustomerSanctionLetter().setSanctionDate(curdate);
			cell.setPhrase(
					new Phrase(String.valueOf(customerdetails1.getCustomerSanctionLetter().getSanctionDate()), font1));
			table.addCell(cell);

			cell.setPhrase(new Phrase("Total loan Amount with Intrest", font));
			table.addCell(cell);

			document.add(table);

			Font titlefont3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10);
			Paragraph paracontent2 = new Paragraph(content2, titlefont3);
			document.add(paracontent2);

			document.close();
			ByteArrayInputStream byt = new ByteArrayInputStream(opt.toByteArray());

			MimeMessage mimemessage = sender.createMimeMessage();

			try {
				MimeMessageHelper mimemessageHelper = new MimeMessageHelper(mimemessage, true);
				mimemessageHelper.setFrom(email.getFromEmail());
				mimemessageHelper.setTo(customerdetails1.getCustomerEmail());
				mimemessageHelper.setSubject("Crystal Finance Ltd. Sanction Letter");
				String text = "Dear " + customerdetails1.getCustomerLastName() + customerdetails1.getCustomerFirstName() + customerdetails1.getCustomerMiddleName()
						+ ",\n" + "\n"
						+ "This letter is to inform you that we have reviewed your request for a credit loan . We are pleased to offer you a credit loan of "
						+ customerdetails1.getCustomerSanctionLetter().getLoanAmountSanctioned() + " for "
						+ customerdetails1.getCustomerSanctionLetter().getLoanTenure() + ".\n" + "\n"
						+ "We are confident that you will manage your credit loan responsibly, and we look forward to your continued business.\n"
						+ "\n"
						+ "Should you have any questions about your credit loan, please do not hesitate to contact us.\n"
						+ "\n" + "Thank you for your interest in our services.";

				mimemessageHelper.setText(text);
				byte[] bytearray = byt.readAllBytes();

				mimemessageHelper.addAttachment("loanSanctionLetter.pdf", new ByteArrayResource(bytearray));
				sender.send(mimemessage);

			} catch (Exception e) {
				e.printStackTrace();
			}

			return cr.save(customerdetails1);

		}
		else {
			throw new PdfNotGenerated();
		}
			
		}

}