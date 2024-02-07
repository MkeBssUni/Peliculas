package dwp.pelis.pelis.modules.movies.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface IMovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByNameIgnoreCase(String name);
}
