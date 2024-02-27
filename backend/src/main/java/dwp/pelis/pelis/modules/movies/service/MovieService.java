package dwp.pelis.pelis.modules.movies.service;

import dwp.pelis.pelis.kernel.ResponseApi;
import dwp.pelis.pelis.modules.categories.model.Category;
import dwp.pelis.pelis.modules.categories.model.ICategoryRepository;
import dwp.pelis.pelis.modules.movies.model.IMovieRepository;
import dwp.pelis.pelis.modules.movies.model.Movie;
import dwp.pelis.pelis.modules.movies.model.MovieDto;
import dwp.pelis.pelis.modules.movies.model.MovieFilterDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MovieService {
    private final IMovieRepository iMovieRepository;
    private final ICategoryRepository iCategoryRepository;

    @Transactional(rollbackFor = {SQLException.class,Exception.class})
    public ResponseApi<Movie> save (MovieDto dto){
        Optional<Movie> movie = iMovieRepository.findByNameIgnoreCase(dto.getName());
        if(movie.isPresent() && movie.get().getReleaseDate() == dto.getReleaseDate()) return new ResponseApi<>(HttpStatus.NOT_FOUND, true, "Movie already exists");

        Optional<Category> category = iCategoryRepository.findById(dto.getCategory().getId());
        if (category.isEmpty()) return new ResponseApi<>(HttpStatus.NOT_FOUND, true, "Category Not found");
        dto.setCategory(category.get());

        if (dto.getReleaseDate() < 1000 || dto.getReleaseDate() > 9999) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Invalid release year format");
        if (dto.getReleaseDate() > LocalDateTime.now().getYear()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Invalid release year");

        Movie newMovie = new Movie();
        newMovie.save(dto);
        return new ResponseApi<>(iMovieRepository.saveAndFlush(newMovie), HttpStatus.CREATED, false, "Movie created");
    }

    @Transactional(readOnly = true)
    public ResponseApi<List<Movie>> getAll(MovieFilterDto dto) {
        if (dto.getStartYear() != null && dto.getEndYear() != null) {
            if (dto.getStartYear() < 1000 || dto.getStartYear() > 9999 || dto.getEndYear() < 1000 || dto.getEndYear() > 9999) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Invalid year format");
            if (dto.getStartYear() > dto.getEndYear()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Invalid release year range");
            if (dto.getStartYear() > LocalDateTime.now().getYear() || dto.getEndYear() > LocalDateTime.now().getYear()) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Invalid year");
        }

        if (dto.getCategory() != null) {
            Optional<Category> category = iCategoryRepository.findById(dto.getCategory());
            if (category.isEmpty()) return new ResponseApi<>(HttpStatus.NOT_FOUND, true, "Category Not found");
        }

        if (!dto.getOrder().equalsIgnoreCase("asc") && !dto.getOrder().equalsIgnoreCase("desc") && !dto.getOrder().equalsIgnoreCase("default")) return new ResponseApi<>(HttpStatus.BAD_REQUEST, true, "Invalid order");

        return new ResponseApi<>(iMovieRepository.findAllFiltered(dto.getName().toLowerCase(), dto.getDirector().toLowerCase(), dto.getStartYear(), dto.getEndYear(), dto.getCategory(), dto.getOrder()), HttpStatus.OK, false, "Movies found");
    }
}
