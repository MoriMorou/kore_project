package ru.morou.korekor.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morou.korekor.persist.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
