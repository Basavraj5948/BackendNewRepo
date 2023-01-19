package com.CrystalFinance.app.serviceImpl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.CrystalFinance.app.exception.CustomerNotFound;
import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.repository.CustomerRepository;
import com.CrystalFinance.app.service.SanctionService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class SanctionServiceImpl implements SanctionService {

	@Autowired
	CustomerRepository cr;
	
	

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
	public void generatePdfReport(CustomerDetails customerdetail) 
	{
		
	}

}