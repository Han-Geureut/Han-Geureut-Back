package hangeureut.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import hangeureut.domain.album.entity.Album;
import hangeureut.domain.album.repository.AlbumPostRepository;
import hangeureut.domain.review.entity.Place;
import hangeureut.domain.review.entity.Review;
import hangeureut.domain.review.repository.PlaceRepository;
import hangeureut.domain.review.repository.ReviewRepository;
import hangeureut.domain.search.web.dto.SearchKeywordResponseDTO;
import hangeureut.domain.user.entity.User;
import hangeureut.domain.user.repository.UserRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchKeywordServiceImpl implements SearchKeywordService {

	private final AlbumPostRepository albumPostRepository;
	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;
	private final ReviewRepository reviewRepository;

	// 검색 메서드
	@Override
	public SearchKeywordResponseDTO.SearchResultDTO searchKeyword(String keyword) {

		List<Album> resultAlbumList = albumPostRepository.findByAlbumNameContaining(keyword);
		List<User> resultUserList = userRepository.findByUserNameContaining(keyword);
		List<Place> resultPlaceList = placeRepository.findAllByPlaceNameContaining(keyword);
		List<Review> resultReviewList = reviewRepository.findByContextContaining(keyword);

		List<SearchKeywordResponseDTO.AlbumInfo> albumInfo = new ArrayList<>();
		for (Album album : resultAlbumList) {
			albumInfo.add(SearchKeywordResponseDTO.AlbumInfo.builder()
				.id(album.getId())
				.albumName(album.getAlbumName())
				.userName(album.getUser().getUserName())
				.mainImg(album.getPhotoImages().get(0).getImageUrl())
				.build());
		}

		List<SearchKeywordResponseDTO.UserInfo> userInfo = new ArrayList<>();
		for (User user : resultUserList) {
			userInfo.add(SearchKeywordResponseDTO.UserInfo.builder()
				.id(user.getId())
				.userId(user.getLoginId())
				.userName(user.getUserName())
				.profileImg(user.getImageUrl())
				.build());
		}

		List<SearchKeywordResponseDTO.PlaceInfo> placeInfo = new ArrayList<>();
		for (Place place : resultPlaceList) {
			placeInfo.add(SearchKeywordResponseDTO.PlaceInfo.builder()
				.id(place.getId())
				.placeName(place.getPlaceName())
				.build());
		}

		List<SearchKeywordResponseDTO.ReviewInfo> reviewInfo = new ArrayList<>();
		for (Review review : resultReviewList) {
			reviewInfo.add(SearchKeywordResponseDTO.ReviewInfo.builder()
				.id(review.getId())
				.rate(review.getStar())
				.context(review.getContext())
				.build());
		}

		return SearchKeywordResponseDTO.SearchResultDTO.builder()
			.albumList(albumInfo)
			.userList(userInfo)
			.placeList(placeInfo)
			.reviewList(reviewInfo)
			.build();
	}

	// 전체 앨범 조회 (추가된 부분)
	@Override
	public List<SearchKeywordResponseDTO.AlbumInfo> getAllPublicAlbums() {

		List<Album> albums = albumPostRepository.findAll();
		List<SearchKeywordResponseDTO.AlbumInfo> result = new ArrayList<>();

		for (Album album : albums) {
			result.add(SearchKeywordResponseDTO.AlbumInfo.builder()
					.id(album.getId())
					.albumName(album.getAlbumName())
					.userName(album.getUser().getUserName())
					.mainImg(
							album.getPhotoImages().isEmpty()
									? null
									: album.getPhotoImages().get(0).getImageUrl()
					)
					.build());
		}

		return result;
	}

	@Override
	public List<SearchKeywordResponseDTO.ReviewInfo> getAllPublicReviews() {

		List<Review> reviews = reviewRepository.findAll();
		List<SearchKeywordResponseDTO.ReviewInfo> result = new ArrayList<>();

		for (Review review : reviews) {
			result.add(SearchKeywordResponseDTO.ReviewInfo.builder()
					.id(review.getId())
					.rate(review.getStar())
					.context(review.getContext())
					.build());
		}

		return result;
	}
}
