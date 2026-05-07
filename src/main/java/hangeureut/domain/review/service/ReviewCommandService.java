package hangeureut.domain.review.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import hangeureut.domain.review.entity.Review;
import hangeureut.domain.review.entity.ReviewImage;
import hangeureut.domain.review.web.dto.ReviewRequestDTO;
import hangeureut.domain.user.entity.User;

public interface ReviewCommandService {
	Review addReview(ReviewRequestDTO.createUserReview request, User user);

	List<ReviewImage> saveReviewImages(List<MultipartFile> request);

	Review addReviewWithPhotoId(ReviewRequestDTO.createUserReview request, User user, Long photoId);
}
