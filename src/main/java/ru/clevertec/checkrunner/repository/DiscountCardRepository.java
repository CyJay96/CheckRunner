package ru.clevertec.checkrunner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.domain.DiscountCard;

import java.util.List;

@Repository
public interface DiscountCardRepository extends JpaRepository<DiscountCard, Long> {

    boolean existsByNumber(Long number);

    List<DiscountCard> findAllByNumber(Long number);
}
