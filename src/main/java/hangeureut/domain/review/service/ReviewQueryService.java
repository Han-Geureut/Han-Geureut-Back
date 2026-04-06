package hangeureut.domain.review.service;

import hangeureut.domain.album.enums.SortStatus;
import hangeureut.domain.review.web.dto.ReviewResponseDTO;

public interface ReviewQueryService {
	ReviewResponseDTO.UserReviewListResultDTO getUserReviewByUserId(Long userId, Integer page, Integer pageCount);

	ReviewResponseDTO.UserReviewListResultDTO getReviewByPlaceId(Long placeId, Integer page, Integer pageCount);

	ReviewResponseDTO.UserReviewListResultDTO getAllReviewsWithSoring(SortStatus sortStatus, Integer page,
		Integer pageCount);

}
