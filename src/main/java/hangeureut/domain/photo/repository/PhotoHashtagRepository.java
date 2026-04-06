package hangeureut.domain.photo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hangeureut.domain.photo.entity.PhotoHashTag;

@Repository
public interface PhotoHashtagRepository extends JpaRepository<PhotoHashTag, Long> {
	List<PhotoHashTag> findByPhotoId(Long PhotoId);
}
