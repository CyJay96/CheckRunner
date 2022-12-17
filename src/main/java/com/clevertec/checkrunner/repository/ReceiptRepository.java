package com.clevertec.checkrunner.repository;

import com.clevertec.checkrunner.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
