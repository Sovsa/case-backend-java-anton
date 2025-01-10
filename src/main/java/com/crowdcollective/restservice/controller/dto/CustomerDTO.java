package com.crowdcollective.restservice.controller.dto;

import com.crowdcollective.restservice.datamodel.Customer;

import java.util.Date;

public record CustomerDTO(Integer customerid,
                          String customerName,
                          String customerType,
                          String orgNr,
                          Date inactivationTime) {

    public CustomerDTO(Customer customer) {
        this(customer.getCustomerid(),
                customer.getCustomerName(),
                customer.getCustomerType().getName(),
                customer.getOrgNr(),
                customer.getInactivationTime());
    }
}
