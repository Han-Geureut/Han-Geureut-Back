package hangeureut.domain.album.service;

import hangeureut.domain.album.web.dto.AlbumPostRequestDTO;
import hangeureut.domain.album.web.dto.AlbumPostResponseDTO;
import hangeureut.domain.user.entity.User;

public interface AlbumPostCommandService {

	AlbumPostResponseDTO.AddAlbumPostResultDTO addAlbum(AlbumPostRequestDTO.addAlbumPostDTO request, User user);

	void deleteAlbum(User user, Long albumId);

	void unlikeAlbum(User user, Long albumId);

	void likeAlbum(User user, Long albumId);

}
