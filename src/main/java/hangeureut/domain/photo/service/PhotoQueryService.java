package hangeureut.domain.photo.service;

import hangeureut.domain.photo.web.dto.PhotoResponseDTO;

public interface PhotoQueryService {
	PhotoResponseDTO.PhotoDetailDTO getPhotoDetail(Long photoId);
}
