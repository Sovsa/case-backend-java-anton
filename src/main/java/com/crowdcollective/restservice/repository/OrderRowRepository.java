package com.crowdcollective.restservice.repository;

import com.crowdcollective.restservice.datamodel.OrderRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRowRepository extends JpaRepository<OrderRow, Integer> {
}
