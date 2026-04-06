package hangeureut.domain.search.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import hangeureut.domain.search.service.PlaceDetailPageService;
import hangeureut.domain.search.service.SearchKeywordService;
import hangeureut.domain.search.web.dto.SearchKeywordResponseDTO;
import hangeureut.domain.search.web.dto.TotalPlaceDetailPageResponseDto;
import hangeureut.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchKeywordController {

	private final SearchKeywordService searchKeywordService;
	private final PlaceDetailPageService placeDetailPageService;

	@GetMapping("/keyword")
	public ApiResponse<SearchKeywordResponseDTO.SearchResultDTO> searchKeyword(@RequestParam String keyword) {

		SearchKeywordResponseDTO.SearchResultDTO searchKeywordResponseDTO = searchKeywordService.searchKeyword(keyword);

		return ApiResponse.onSuccess(searchKeywordResponseDTO);
	}

	@GetMapping("/placeDetail/{placeId}")
	public ApiResponse<TotalPlaceDetailPageResponseDto.AllPlaceDetailPageResponseDto> getPlaceDetail(
		@PathVariable(name = "placeId") Long placeId) { // pathVariable로 바꾸기

		TotalPlaceDetailPageResponseDto.AllPlaceDetailPageResponseDto allPlaceDetailPageResponseDto = new TotalPlaceDetailPageResponseDto.AllPlaceDetailPageResponseDto();
		allPlaceDetailPageResponseDto.setPlaceDetail(placeDetailPageService.getPlaceDetail(placeId));
		allPlaceDetailPageResponseDto.setReviewList(placeDetailPageService.getAllReviews(placeId));

		return ApiResponse.onSuccess(allPlaceDetailPageResponseDto);
	}
}
