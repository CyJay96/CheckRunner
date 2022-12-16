package com.clevertec.checkrunner.repository;

import com.clevertec.checkrunner.domain.ReceiptProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptProductRepository extends JpaRepository<ReceiptProduct, Long> {
}
