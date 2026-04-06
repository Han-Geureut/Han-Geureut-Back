package hangeureut.domain.album.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import hangeureut.domain.album.service.GoogleMapsPlatformService;
import hangeureut.domain.album.web.dto.GoogleMapsPlatformResponseDTO;
import hangeureut.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album/GoogleMapsPlatform")
public class GoogleMapsPlatformController {

	private final GoogleMapsPlatformService googleMapsPlatformService;

	@GetMapping("{albumId}")
	public ApiResponse<GoogleMapsPlatformResponseDTO.WayPointsList> getWayPoints(
		@PathVariable(name = "albumId") Long albumId) {
		GoogleMapsPlatformResponseDTO.WayPointsList result = googleMapsPlatformService.getWayPoints(albumId);

		return ApiResponse.onSuccess(result);
	}
}
