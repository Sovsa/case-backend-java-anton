package com.crowdcollective.restservice.service;

import com.crowdcollective.restservice.controller.dto.CustomerDTO;
import com.crowdcollective.restservice.datamodel.Campaign;
import com.crowdcollective.restservice.datamodel.Customer;
import com.crowdcollective.restservice.datamodel.CustomerType;
import com.crowdcollective.restservice.datamodel.Product;
import com.crowdcollective.restservice.repository.CampaignRepository;
import com.crowdcollective.restservice.repository.CustomerRepository;
import com.crowdcollective.restservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CampaignService campaignService;

    public Customer createCustomer(String customerName,
                                   CustomerType customerType,
                                   String orgNr) {
        Customer customer = new Customer(customerName, customerType, orgNr);
        if (customerType.equals(CustomerType.SMALL_ENTERPRISE) || customerType.equals(CustomerType.LARGE_ENTERPRISE)) {
            createNewEnterpriseCampaign(customer);

            if (customerType.equals(CustomerType.LARGE_ENTERPRISE)) {
                createNewLargeEnterpriseCampaign(customer);
            }
        }

        Customer c = customerRepository.save(customer);
        return c;
    }

    public Customer getCustomer(Integer customerid) {
        return customerRepository.findById(customerid).orElse(null);
    }

    public void deleteCustomer(Integer customerid) {
        Customer customer = customerRepository.findById(customerid).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Could not find customer");
        }
        campaignService.endAllCustomerCampaigns(customerid);

        customer.setInactivationTime(new Date());
        customerRepository.save(customer);
    }

    public Customer updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(customerDTO.customerid()).orElse(null);

        if (customer == null) {
            throw new IllegalArgumentException("Could not find customer");
        }

        if (!isNullOrEmpty(customerDTO.customerName())) {
            customer.setCustomerName(customer.getCustomerName());
        }
        if (!isNullOrEmpty(customerDTO.customerType())) {
            CustomerType customerType = CustomerType.getCustomerTypeFromName(customerDTO.customerType());
            if (customerType == null) {
                throw new IllegalArgumentException("""
                        Incorrect customertype. Accepted customertypes are:
                        "small_enterprise",
                        large_enterprise",
                        "private" """);
            }
            customer.setCustomerType(customerType);
        }
        if (!isNullOrEmpty(customerDTO.orgNr())) {
            customer.setOrgNr(customerDTO.orgNr());
        }

        return customerRepository.save(customer);
     }

    private void createNewEnterpriseCampaign(Customer customer) {
        Campaign campaign = new Campaign(new Date(),
                null,
                0.90,
                customer,
                null);
        customer.getCampaigns().add(campaign);
    }

    private void createNewLargeEnterpriseCampaign(Customer customer) {
        Product penProduct = productRepository.findById(ProductService.PEN_PRODUCT_ID).orElse(null);
        Product paperProduct = productRepository.findById(ProductService.PAPER_PRODUCT_ID).orElse(null);

        Campaign penCampaign = new Campaign(new Date(),
                null,
                0.80,
                customer,
                penProduct);
        Campaign paperCampaign = new Campaign(new Date(),
                null,
                0.80,
                customer,
                paperProduct);

        customer.getCampaigns().addAll(Arrays.asList(paperCampaign, penCampaign));
    }

    private boolean isNullOrEmpty(String string) {
        return string == null && string.trim().isEmpty();
    }
}
