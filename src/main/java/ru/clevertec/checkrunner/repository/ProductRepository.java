package ru.clevertec.checkrunner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.checkrunner.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
