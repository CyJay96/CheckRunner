package ru.clevertec.checkrunner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.domain.DiscountCard;

import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {

    boolean existsByNumber(Long number);

    Optional<DiscountCard> findByNumber(Long number);
}
