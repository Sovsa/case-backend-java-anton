package com.crowdcollective.restservice.repository;

import com.crowdcollective.restservice.datamodel.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
