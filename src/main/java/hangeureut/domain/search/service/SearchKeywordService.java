package hangeureut.domain.search.service;

import hangeureut.domain.search.web.dto.SearchKeywordResponseDTO;
import java.util.List;

public interface SearchKeywordService {
    SearchKeywordResponseDTO.SearchResultDTO searchKeyword(String keyword);
    
    // 추가
    List<SearchKeywordResponseDTO.AlbumInfo> getAllPublicAlbums();
    List<SearchKeywordResponseDTO.ReviewInfo> getAllPublicReviews();
}
