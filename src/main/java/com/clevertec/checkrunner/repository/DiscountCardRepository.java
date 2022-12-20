package com.clevertec.checkrunner.repository;

import com.clevertec.checkrunner.domain.DiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {

    boolean existsByNumber(Long number);

    Optional<DiscountCard> findByNumber(Long number);

}
