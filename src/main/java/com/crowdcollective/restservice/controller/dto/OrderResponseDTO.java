package com.crowdcollective.restservice.controller.dto;

import com.crowdcollective.restservice.datamodel.Customer;
import com.crowdcollective.restservice.datamodel.Order;
import com.crowdcollective.restservice.datamodel.OrderRow;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public record OrderResponseDTO(Integer orderid,
                               Integer customerid,
                               Set<OrderRowResponseDTO> orderRows,
                               BigDecimal totalSum,
                               BigDecimal totalDiscount) {

    public OrderResponseDTO(Order order,
                            Set<OrderRowResponseDTO> orderRows,
                            BigDecimal totalSum,
                            BigDecimal totalDiscountSum) {
        this(order.getOrderid(),
                order.getCustomer().getCustomerid(),
                orderRows,
                totalSum.setScale(4, RoundingMode.HALF_UP),
                totalDiscountSum.setScale(4, RoundingMode.HALF_UP));
    }
}
