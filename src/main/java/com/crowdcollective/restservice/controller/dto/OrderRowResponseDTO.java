package com.crowdcollective.restservice.controller.dto;

import com.crowdcollective.restservice.datamodel.OrderRow;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record OrderRowResponseDTO(Integer productid,
                                  String name,
                                  Integer amount,
                                  BigDecimal pricePerArticle,
                                  BigDecimal totalSum,
                                  BigDecimal totalDiscountSum) {

    public OrderRowResponseDTO(OrderRow orderRow) {
        this(orderRow.getProduct().getProductid(),
                orderRow.getDescription(),
                orderRow.getAmount(),
                orderRow.getPricePerArticle(),
                orderRow.getTotalSum().setScale(4, RoundingMode.HALF_UP),
                orderRow.getTotalDiscountSum().setScale(4, RoundingMode.HALF_UP));
    }
}
