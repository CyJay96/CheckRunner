package com.clevertec.checkrunner.repository;

import com.clevertec.checkrunner.domain.CheckProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckProductRepository extends JpaRepository<CheckProduct, Long> {
}
