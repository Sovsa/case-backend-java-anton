package com.crowdcollective.restservice.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productid", unique = true, nullable = false)
    private Integer productid;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    @Column(name = "unitprice", precision = 16, scale = 4)
    private BigDecimal pricePerArticle;

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPricePerArticle() {
        return pricePerArticle;
    }

    public void setPricePerArticle(BigDecimal pricePerUnit) {
        this.pricePerArticle = pricePerUnit;
    }

    @PrePersist
    @PreUpdate
    public void pricePrecisionConvertion() {
        this.pricePerArticle.setScale(4, RoundingMode.HALF_UP);
    }
}
