package pl.maprzybysz.imagehostingvaadin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maprzybysz.imagehostingvaadin.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
