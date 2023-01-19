package com.CrystalFinance.app.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.CrystalFinance.app.model.Email;
import com.CrystalFinance.app.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender sender;
	
	@Override
	public void sendMail(Email e) 
	{
	try {
		SimpleMailMessage message=new SimpleMailMessage();
		      message.setTo(e.getToEmail());
		      message.setFrom(e.getFromEmail());
		      message.setSubject(e.getSubject());
		      message.setText(e.getText());
		      
		      sender.send(message);
	} catch (MailException e1) {
		System.out.println("Email Failed to send");
		e1.printStackTrace();
	}		
	}
} 
