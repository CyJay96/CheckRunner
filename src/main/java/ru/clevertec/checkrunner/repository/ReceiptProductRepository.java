package ru.clevertec.checkrunner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.domain.ReceiptProduct;

@Repository
public interface ReceiptProductRepository extends JpaRepository<ReceiptProduct, Long> {
}
