package hangeureut.domain.search.web.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import hangeureut.domain.review.web.dto.ReviewResponseDTO;

public class TotalPlaceDetailPageResponseDto {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class AllPlaceDetailPageResponseDto {
		private PlaceDetailResponseDto.PlaceDetail placeDetail;
		private List<ReviewResponseDTO.ReviewQueryResultDTO> reviewList;
	}
}
