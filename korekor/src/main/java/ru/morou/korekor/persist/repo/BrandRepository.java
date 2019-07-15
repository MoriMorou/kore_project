package ru.morou.korekor.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morou.korekor.persist.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
