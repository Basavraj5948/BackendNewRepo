package com.CrystalFinance.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.CrystalFinance.app.model.Enquirymodel;

@Repository
public interface EnquiryRepository extends JpaRepository<Enquirymodel, Integer>{
 
   public Iterable<Enquirymodel> findAllByCibilStatus(String cibilStatus);	
}
