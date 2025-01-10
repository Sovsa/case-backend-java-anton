package com.crowdcollective.restservice.datamodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity(name = "OrderRow")
@Table(name = "orderrow")
public class OrderRow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderrowid", unique = true, nullable = false)
    private Integer orderRowId;

    @Column(insertable=false, updatable=false, nullable = false)
    private Integer orderid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderid")
    private Order order;

    @Column(name = "description")
    private String description;

    @Column(name = "unitprice", precision = 16, scale = 4)
    private BigDecimal pricePerArticle;

    @Column(name = "amount")
    private Integer amount;

    @Column(name = "totalsum", precision = 16, scale = 4)
    private BigDecimal totalSum;

    @Column(name = "totaldiscount", precision = 16, scale = 4)
    private BigDecimal totalDiscountSum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid")
    private Product product;

    @Column(name = "productid", insertable=false, updatable=false)

    private Integer productid;

    public void setOrderRowId(Integer id) {
        this.orderRowId = id;
    }

    public Integer getOrderRowId() {
        return orderRowId;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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

    public void setPricePerArticle(BigDecimal pricePerArticle) {
        this.pricePerArticle = pricePerArticle;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public BigDecimal getTotalDiscountSum() {
        return totalDiscountSum;
    }

    public void setTotalDiscountSum(BigDecimal totalDiscountSum) {
        this.totalDiscountSum = totalDiscountSum;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    @PrePersist
    @PreUpdate
    public void pricePrecisionConvertion() {
        this.pricePerArticle.setScale(4, RoundingMode.HALF_UP);
        this.totalSum.setScale(4, RoundingMode.HALF_UP);
        this.totalDiscountSum.setScale(4, RoundingMode.HALF_UP);
    }
}
