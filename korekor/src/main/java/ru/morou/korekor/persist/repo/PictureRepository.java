package ru.morou.korekor.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morou.korekor.persist.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
