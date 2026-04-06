package hangeureut.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hangeureut.domain.review.entity.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
