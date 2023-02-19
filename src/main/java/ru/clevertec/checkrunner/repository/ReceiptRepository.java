package ru.clevertec.checkrunner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.domain.Receipt;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {
}
