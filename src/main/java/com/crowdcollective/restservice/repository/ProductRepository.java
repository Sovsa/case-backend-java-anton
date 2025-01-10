package com.crowdcollective.restservice.repository;

import com.crowdcollective.restservice.datamodel.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
