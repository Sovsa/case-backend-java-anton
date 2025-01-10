package com.crowdcollective.restservice.datamodel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "Customer")
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerid", unique = true, nullable = false)
    private Integer customerid;
    @Column(name = "customername")
    private String customerName;
    @Enumerated(EnumType.STRING)
    @Column(name = "customertype")
    private CustomerType customerType;
    @Column(name = "orgnr", updatable = false, unique = true)
    private String orgNr;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customerid")
    private Set<Campaign> campaigns;
    @Column(name ="inactivationtime")
    private Date inactivationTime;

    public Customer(String customerName,
                    CustomerType customerType,
                    String orgNr) {
        this.customerName = customerName;
        this.customerType = customerType;
        this.orgNr = orgNr;
        this.campaigns = new HashSet<>();
    }

    public Customer() {
        this.campaigns = new HashSet<>();
    }

    public void setCustomerid(Integer customerid) {
        this.customerid = customerid;
    }

    public Integer getCustomerid() {
        return customerid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public String getOrgNr() {
        return orgNr;
    }

    public void setOrgNr(String orgNr) {
        this.orgNr = orgNr;
    }

    public Set<Campaign> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    public Date getInactivationTime() {
        return inactivationTime;
    }

    public void setInactivationTime(Date inactivationTime) {
        this.inactivationTime = inactivationTime;
    }
}
