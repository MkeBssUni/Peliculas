package dwp.pelis.pelis.modules.movies.controller;

import dwp.pelis.pelis.kernel.ResponseApi;
import dwp.pelis.pelis.modules.movies.model.Movie;
import dwp.pelis.pelis.modules.movies.model.MovieDto;
import dwp.pelis.pelis.modules.movies.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cinema/movies")
@AllArgsConstructor
@CrossOrigin(value = {"*"})
public class MovieController {
    private final MovieService movieService;

    @PostMapping(value = "/")
    public ResponseEntity<ResponseApi<Movie>> save(@Validated(MovieDto.Save.class) @RequestBody MovieDto dto){
        ResponseApi<Movie> responseApi = movieService.save(dto);
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }

    @GetMapping(value = "/")
    public ResponseEntity<ResponseApi<List<Movie>>> getAll(){
        ResponseApi<List<Movie>> responseApi = movieService.getAll();
        return new ResponseEntity<>(responseApi, responseApi.getStatus());
    }
}
