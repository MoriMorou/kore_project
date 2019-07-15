package ru.morou.korekor.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.morou.korekor.controller.repr.CategoryRepr;
import ru.morou.korekor.persist.model.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select new ru.morou.korekor.controller.repr.CategoryRepr(c.id, c.name, count(p.id)) " +
            "from Category c " +
            "left join c.products p " +
            "group by c.id, c.name")
    List<CategoryRepr> getAllCategoryRepr();
}
