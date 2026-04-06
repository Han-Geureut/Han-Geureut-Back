package hangeureut.domain.album.service;

import org.springframework.data.domain.Page;

import hangeureut.domain.album.entity.Album;
import hangeureut.domain.album.enums.SortStatus;
import hangeureut.domain.album.web.dto.AlbumPostResponseDTO;

public interface AlbumPostQueryService {
	AlbumPostResponseDTO.AlbumPostPreviewResultPageDTO getAlbumPage(SortStatus sortStatus, Integer page,
		Integer pageCount);

	Page<Album> getUserAlbumPage(Long userId, SortStatus sortStatus, Integer page, Integer pageCount);

	Long getAlbumCount(Long userId);

	AlbumPostResponseDTO.AlbumDetailResponseDTO getAlbumDetail(Long albumId);
}

