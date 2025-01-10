package com.crowdcollective.restservice.service;

import com.crowdcollective.restservice.datamodel.Campaign;
import com.crowdcollective.restservice.datamodel.Customer;
import com.crowdcollective.restservice.datamodel.Order;
import com.crowdcollective.restservice.datamodel.OrderRow;
import com.crowdcollective.restservice.datamodel.OrderState;
import com.crowdcollective.restservice.datamodel.Product;
import com.crowdcollective.restservice.repository.CustomerRepository;
import com.crowdcollective.restservice.repository.OrderRepository;
import com.crowdcollective.restservice.repository.OrderRowRepository;
import com.crowdcollective.restservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(Map<Integer, Integer> productIdToAmountMap, Integer customerid) {
        Order order = new Order();
        Customer customer = customerRepository.findById(customerid).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Could not find customer: " + customerid);
        }
        order.setCustomer(customer);
        order.setOrderState(OrderState.ADDED);
        addOrderRowsToOrder(productIdToAmountMap, order);
        addBiCycleProduct(order);

        return orderRepository.save(order);
    }

    public Order getOrder(Integer orderid) {
        Order order = orderRepository.findById(orderid).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("Could not find order");
        }
        return order;
    }

    public Order deleteOrder(Integer orderid) {
        Order order = orderRepository.findById(orderid).orElse(null);
        if (order == null) {
            throw new IllegalArgumentException("Could not find order");
        }
        order.setOrderState(OrderState.CANCELED);
        return orderRepository.save(order);
    }

    private void addBiCycleProduct(Order order) {
        BigDecimal totalSum = new BigDecimal(0);
        BigDecimal freeBicycleThreshold = new BigDecimal(10000.0000);
        for (OrderRow orderRow : order.getOrderRows()) {
            totalSum.add(orderRow.getTotalSum());
        }
        if (totalSum.compareTo(freeBicycleThreshold) < 0) {
            Product bicycle = productRepository.findById(5).orElse(null);
            if (bicycle == null) {
                throw new RuntimeException("Could not add bicycle product");
            }

            OrderRow orderRow = new OrderRow();
            orderRow.setAmount(1);
            orderRow.setOrder(order);
            orderRow.setProduct(bicycle);
            orderRow.setPricePerArticle(new BigDecimal(0));
            orderRow.setDescription(bicycle.getName());
            orderRow.setTotalSum(new BigDecimal(0));
            orderRow.setTotalDiscountSum(new BigDecimal(0));
            order.getOrderRows().add(orderRow);
        }
    }

    private void addOrderRowsToOrder(Map<Integer, Integer> productIdToAmountMap, Order order) {
        for (Map.Entry<Integer, Integer> integerIntegerEntry : productIdToAmountMap.entrySet()) {
            Integer productid = integerIntegerEntry.getKey();
            Integer amount = integerIntegerEntry.getValue();

            Product product = productRepository.findById(productid).orElse(null);
            if (product == null) {
                throw new IllegalArgumentException("Could not find product: " + productid);
            }
            Campaign campaignToUseForProduct = getCampaign(order, product);

            OrderRow orderRow = new OrderRow();
            orderRow.setAmount(amount);
            orderRow.setOrder(order);
            orderRow.setProduct(product);
            BigDecimal pricePerArticle = product.getPricePerArticle();
            orderRow.setPricePerArticle(pricePerArticle);
            orderRow.setDescription(product.getName());
            BigDecimal totalSum = calculateTotalSum(pricePerArticle, amount, campaignToUseForProduct);
            orderRow.setTotalSum(totalSum);
            BigDecimal discountedSum = pricePerArticle.multiply(new BigDecimal(amount)).subtract(totalSum);
            orderRow.setTotalDiscountSum(discountedSum);
            order.getOrderRows().add(orderRow);
        }
    }

    private BigDecimal calculateTotalSum(BigDecimal pricePerArticle, Integer amount, Campaign campaignToUseForProduct) {
        if (campaignToUseForProduct == null) {
            return pricePerArticle.multiply(new BigDecimal(amount));
        } else {
            return pricePerArticle.multiply(new BigDecimal(amount)).multiply(new BigDecimal(campaignToUseForProduct.getDiscountPercentage()));
        }
    }

    private static Campaign getCampaign(Order order, Product product) {
        Campaign campaignToUseForProduct = null;
        Campaign campaignForAllProducts = null;
        for (Campaign campaign : order.getCustomer().getCampaigns()) {
            if(isActiveCampaign(campaign)) {
                if (campaign.getProduct() == null) {
                    campaignForAllProducts = campaign;
                    continue;
                }

                if (campaign.getProductid() == product.getProductid()) {
                    campaignToUseForProduct = campaign;
                }
            }
        }
        if (campaignToUseForProduct == null) {
            campaignToUseForProduct = campaignForAllProducts;
        }

        return campaignToUseForProduct;
    }

    private static boolean isActiveCampaign(Campaign campaign) {
        return campaign.getStopDate() == null || campaign.getStopDate().before(new Date());
    }
}
