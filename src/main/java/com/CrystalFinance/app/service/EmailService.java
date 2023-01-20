package com.CrystalFinance.app.service;

import com.CrystalFinance.app.model.CustomerDetails;
import com.CrystalFinance.app.model.Email;
import com.CrystalFinance.app.model.SanctionLetter;

public interface EmailService
{
  public void sendMail(Email e);

  public SanctionLetter sendSantionLetterMail(CustomerDetails customerDetails);
}
