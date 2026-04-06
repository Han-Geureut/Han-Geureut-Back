package hangeureut.domain.search.service;

import hangeureut.domain.search.web.dto.SearchKeywordResponseDTO;

public interface SearchKeywordService {
    SearchKeywordResponseDTO.SearchResultDTO searchKeyword(String keyword);
}
