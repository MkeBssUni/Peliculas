package dwp.pelis.pelis.modules.movies.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface IMovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByNameIgnoreCase(String name);
    @Query(value = "SELECT m FROM Movie m WHERE "
            + "(COALESCE(:name, '') = '' OR LOWER(m.name) LIKE %:name%) AND "
            + "(COALESCE(:director, '') = '' OR LOWER(m.director) LIKE %:director%) AND "
            + "(COALESCE(:startYear, 0) = 0 OR m.releaseDate >= :startYear) AND "
            + "(COALESCE(:endYear, 0) = 0 OR m.releaseDate <= :endYear) AND "
            + "(COALESCE(:categoryId, 0) = 0 OR m.category.id = :categoryId)"
            + "ORDER BY "
            + "CASE WHEN LOWER(:order) = 'asc' THEN m.releaseDate END ASC, "
            + "CASE WHEN LOWER(:order) = 'desc' THEN m.releaseDate END DESC, "
            + "CASE WHEN LOWER(:order) = 'default' THEN m.id END DESC")
    List<Movie> findAllFiltered(String name, String director, Integer startYear, Integer endYear, Long categoryId, String order);
}
