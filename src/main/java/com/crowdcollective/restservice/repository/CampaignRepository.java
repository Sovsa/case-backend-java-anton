package com.crowdcollective.restservice.repository;

import com.crowdcollective.restservice.datamodel.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

    @Query("SELECT c FROM Campaign c WHERE c.customerid = ?1")
    List<Campaign> getAllCampaignsForCustomer(Integer customerid);
}
