package hangeureut.domain.photo.service;

import org.springframework.web.multipart.MultipartFile;

import hangeureut.domain.photo.entity.Photo;

public interface PhotoCommandService {
	Photo savePhotoImages(MultipartFile image);
}
