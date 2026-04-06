package hangeureut.domain.photo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hangeureut.domain.photo.entity.Uuid;

public interface UuidRepository extends JpaRepository<Uuid, Long> {

}
