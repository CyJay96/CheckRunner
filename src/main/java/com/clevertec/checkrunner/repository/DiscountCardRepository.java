package com.clevertec.checkrunner.repository;

import com.clevertec.checkrunner.domain.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {
}
