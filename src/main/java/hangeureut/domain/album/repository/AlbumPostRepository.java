package hangeureut.domain.album.repository;

import hangeureut.domain.album.entity.Album;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumPostRepository extends JpaRepository<Album, Long> {
    Page<Album> findAll(Pageable pageable);

    @Query(
        value = "SELECT a FROM Album a LEFT JOIN a.albumLikedList al GROUP BY a ORDER BY COUNT(al) DESC, a.createdAt DESC",
        countQuery = "SELECT COUNT(a) FROM Album a"
    )
    Page<Album> findAllOrderByLikeCountDesc(Pageable pageable);

    Page<Album> findByUserId(Long userId, Pageable pageable);

    Long countByUserId(Long userId);

    Optional<Album> findById(Long albumId);

    List<Album> findByAlbumNameContaining(String albumName);
}
