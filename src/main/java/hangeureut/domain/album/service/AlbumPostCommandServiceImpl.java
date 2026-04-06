package hangeureut.domain.album.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import hangeureut.domain.album.converter.AlbumPostConverter;
import hangeureut.domain.album.entity.Album;
import hangeureut.domain.album.entity.mapping.AlbumLiked;
import hangeureut.domain.album.exception.AlbumNotExistException;
import hangeureut.domain.album.repository.AlbumLikedRepository;
import hangeureut.domain.album.repository.AlbumPostRepository;
import hangeureut.domain.album.web.dto.AlbumPostRequestDTO;
import hangeureut.domain.album.web.dto.AlbumPostResponseDTO;
import hangeureut.domain.photo.entity.Photo;
import hangeureut.domain.photo.service.PhotoCommandService;
import hangeureut.domain.review.entity.Place;
import hangeureut.domain.review.repository.PlaceRepository;
import hangeureut.domain.user.entity.User;
import hangeureut.global.enums.statuscode.ErrorStatus;
import hangeureut.global.exception.GeneralException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlbumPostCommandServiceImpl implements AlbumPostCommandService {
	private static final Logger log = LogManager.getLogger(AlbumPostCommandServiceImpl.class);

	private final AlbumPostRepository albumPostRepository;
	private final PlaceRepository placeRepository;
	private final AlbumLikedRepository albumLikedRepository;
	private final PhotoCommandService photoCommandService;

	@Override
	@Transactional
	public AlbumPostResponseDTO.AddAlbumPostResultDTO addAlbum(AlbumPostRequestDTO.addAlbumPostDTO request, User user) {

		ForkJoinPool commonPool = ForkJoinPool.commonPool();
		int parallelism = commonPool.getParallelism();

		log.info("Thread Pool Size = {}", parallelism);
		Album albumPost = AlbumPostConverter.toAlbumPost(request);
		user.addAlbum(albumPost);

		List<CompletableFuture<Photo>> createPhotoFutures = new ArrayList<>();

		for (MultipartFile image : request.getImages()) {
			CompletableFuture<Photo> future = CompletableFuture.supplyAsync(
				() -> photoCommandService.savePhotoImages(image));
			createPhotoFutures.add(future);
		}

		// 모든 비동기 작업 완료 후 결과 처리
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(
			createPhotoFutures.toArray(new CompletableFuture[createPhotoFutures.size()]));

		// 모든 작업 완료 후 결과를 처리하기 위해 thenApply를 사용
		CompletableFuture<List<Photo>> allResultsFuture = allFutures.thenApply(v -> {

			List<Photo> results = new ArrayList<>();
			for (CompletableFuture<Photo> future : createPhotoFutures) {
				try {
					results.add(future.get());
				} catch (InterruptedException | ExecutionException e) {
					log.error("photo 데이터를 합치는 도중 	에러가 발생했습니다. 1 {}", e.getMessage());
					throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
				}
			}
			return results;
		});

		for (Photo photo : allResultsFuture.join()) {
			if (photo.getPlace() != null) {
				Place place = photo.getPlace();

				Optional<Place> placeOptional = placeRepository.findByPlaceName(place.getPlaceName());
				if (placeOptional.isPresent()) {
					place = placeOptional.get();
					place.addPhoto(photo);
				} else {
					Place save = placeRepository.save(place);
					save.addPhoto(photo);

				}
			}

			albumPost.addPhoto(photo);
		}

		albumPost = albumPostRepository.save(albumPost);


		return AlbumPostConverter.toAddAlbumPostResultDTO(albumPost);
	}

	@Override
	@Transactional
	public void deleteAlbum(User user, Long albumId) {
		Album album = albumPostRepository.findById(albumId)
			.orElseThrow(() -> new GeneralException(ErrorStatus._ALBUM_NOT_EXIST));

		if (album.getUser().getId() != user.getId()) {
			throw new GeneralException(ErrorStatus._ALBUM_FORBIDEN);
		}

		albumPostRepository.deleteById(albumId);
	}

	@Override
	@Transactional
	public void likeAlbum(User user, Long albumId) {
		Album album = albumPostRepository.findById(albumId).orElseThrow(() -> new AlbumNotExistException());
		if (albumLikedRepository.findByUserIdAndAlbumId(user.getId(), albumId).isPresent())
			return;
		AlbumLiked albumLiked = AlbumLiked.builder()
			.album(album)
			.user(user)
			.build();

		albumLikedRepository.save(albumLiked);
	}

	@Override
	@Transactional
	public void unlikeAlbum(User user, Long albumId) {
		albumPostRepository.findById(albumId).orElseThrow(() -> new AlbumNotExistException());
		albumLikedRepository.findByUserIdAndAlbumId(user.getId(), albumId).ifPresent(albumLiked -> {
			albumLikedRepository.delete(albumLiked);
		});
	}

}
