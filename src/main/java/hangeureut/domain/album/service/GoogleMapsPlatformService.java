package hangeureut.domain.album.service;

import hangeureut.domain.album.web.dto.GoogleMapsPlatformResponseDTO;

public interface GoogleMapsPlatformService {
    GoogleMapsPlatformResponseDTO.WayPointsList getWayPoints(Long albumId);
}
