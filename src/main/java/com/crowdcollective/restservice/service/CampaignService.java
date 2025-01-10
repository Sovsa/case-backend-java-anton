package com.crowdcollective.restservice.service;

import com.crowdcollective.restservice.datamodel.Campaign;
import com.crowdcollective.restservice.repository.CampaignRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;


    public void endAllCustomerCampaigns(Integer customerid) {
        List<Campaign> campaigns = campaignRepository.getAllCampaignsForCustomer(customerid);

        for (Campaign campaign : campaigns) {
            if (campaign.getStopDate() == null || campaign.getStopDate().after(new Date())) {
                campaign.setStopDate(new Date());
            }
        }
        campaignRepository.saveAll(campaigns);
    }
}
