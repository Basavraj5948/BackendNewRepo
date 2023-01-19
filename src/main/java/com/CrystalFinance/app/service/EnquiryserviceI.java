package com.CrystalFinance.app.service;

import java.util.Optional;

import com.CrystalFinance.app.model.Enquirymodel;

public interface EnquiryserviceI {

	public Enquirymodel saveEnquiry(Enquirymodel m);

	public Iterable<Enquirymodel> getEnquiry(String CIBILStatus);

	public Optional<Enquirymodel> getSingleEnquiry(Integer enqid);

	public Optional<Enquirymodel> findById(Integer enquiryId);

}
