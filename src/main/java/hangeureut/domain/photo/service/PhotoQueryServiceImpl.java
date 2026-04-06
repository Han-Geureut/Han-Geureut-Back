package hangeureut.domain.photo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import hangeureut.domain.photo.converter.PhotoConverter;
import hangeureut.domain.photo.entity.Photo;
import hangeureut.domain.photo.entity.PhotoHashTag;
import hangeureut.domain.photo.exception.PhotoNotExistException;
import hangeureut.domain.photo.repository.PhotoHashtagRepository;
import hangeureut.domain.photo.repository.PhotoRepository;
import hangeureut.domain.photo.web.dto.PhotoResponseDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhotoQueryServiceImpl implements PhotoQueryService {
	private final PhotoRepository photoRepository;
	private final PhotoHashtagRepository photoHashtagRepository;

	@Override
	public PhotoResponseDTO.PhotoDetailDTO getPhotoDetail(Long photoId) {
		Photo photo = photoRepository.findById(photoId).orElseThrow(() -> new PhotoNotExistException());
		List<PhotoHashTag> photoHashTagList = photoHashtagRepository.findByPhotoId(photo.getId());
		return PhotoConverter.toPhotoDetailDTO(photo, photoHashTagList);
	}
}
