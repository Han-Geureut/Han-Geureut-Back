package hangeureut.domain.search.service;

import java.util.List;

import hangeureut.domain.review.web.dto.ReviewResponseDTO;
import hangeureut.domain.search.web.dto.PlaceDetailResponseDto;

public interface PlaceDetailPageService {
	PlaceDetailResponseDto.PlaceDetail getPlaceDetail(Long placeId);

	List<ReviewResponseDTO.ReviewQueryResultDTO> getAllReviews(Long placeDetailRequestDto);

}
