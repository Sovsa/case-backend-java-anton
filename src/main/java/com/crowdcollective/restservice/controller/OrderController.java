package com.crowdcollective.restservice.controller;

import com.crowdcollective.restservice.controller.dto.OrderRequestDTO;
import com.crowdcollective.restservice.controller.dto.OrderResponseDTO;
import com.crowdcollective.restservice.controller.dto.OrderRowRequestDTO;
import com.crowdcollective.restservice.controller.dto.OrderRowResponseDTO;
import com.crowdcollective.restservice.datamodel.Order;
import com.crowdcollective.restservice.datamodel.OrderRow;
import com.crowdcollective.restservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping()
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequestDTO orderdto,
                                              @RequestParam(name = "customerid") Integer customerid) {
        Map<Integer, Integer> productIdToAmountMap = new HashMap<>();

        for (OrderRowRequestDTO orderRowDTO : orderdto.orderRows()) {
            productIdToAmountMap.put(orderRowDTO.productid(), orderRowDTO.amount());
        }

        Order order = orderService.createOrder(productIdToAmountMap, customerid);
        OrderResponseDTO orderResponseDTO = getOrderResponseDTO(order);

        return ResponseHandler.generateResponse("Successfully placed order", HttpStatus.OK, orderResponseDTO);
    }

    @GetMapping
    public ResponseEntity<Object> getOrder(@RequestParam Integer orderid) {
        try {
            Order order = orderService.getOrder(orderid);
            return ResponseHandler.generateResponse("", HttpStatus.OK, getOrderResponseDTO(order));
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteOrder(@RequestParam Integer orderid) {
        try {
            orderService.deleteOrder(orderid);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.NOT_FOUND, null);
        }
        return ResponseHandler.generateResponse("Successfully deleted order", HttpStatus.OK, null);
    }

    @PostMapping
    public ResponseEntity<Object> updateOrder(@RequestBody OrderRequestDTO orderRequestDTO,
                                              @RequestParam(name = "orderid") Integer orderid) {
        Order order = orderService.deleteOrder(orderid);

        Map<Integer, Integer> productIdToAmountMap = new HashMap<>();

        for (OrderRowRequestDTO orderRowDTO : orderRequestDTO.orderRows()) {
            productIdToAmountMap.put(orderRowDTO.productid(), orderRowDTO.amount());
        }

        Order newOrder = orderService.createOrder(productIdToAmountMap, order.getCustomer().getCustomerid());

        return ResponseHandler.generateResponse("Successfully updated order", HttpStatus.OK, getOrderResponseDTO(newOrder));
    }

    private static OrderResponseDTO getOrderResponseDTO(Order order) {
        Set<OrderRowResponseDTO> orderRowResponseDTOS = new HashSet<>();
        BigDecimal totalSum = new BigDecimal(0);
        BigDecimal totalDiscountSum = new BigDecimal(0);
        for (OrderRow orderRow : order.getOrderRows()) {
            orderRowResponseDTOS.add(new OrderRowResponseDTO(orderRow));
            totalSum = totalSum.add(orderRow.getTotalSum());
            totalDiscountSum = totalDiscountSum.add(orderRow.getTotalDiscountSum());
        }

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO(order,
                orderRowResponseDTOS,
                totalSum,
                totalDiscountSum);
        return orderResponseDTO;
    }
}
