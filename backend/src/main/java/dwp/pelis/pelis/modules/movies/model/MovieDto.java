package dwp.pelis.pelis.modules.movies.model;

import dwp.pelis.pelis.modules.categories.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    @NotBlank(groups = {Save.class})
    private String name;
    @NotBlank(groups = {Save.class})
    private String director;
    @NotNull(groups = {Save.class})
    private int releaseDate;
    @NotBlank(groups = {Save.class})
    private String description;
    @NotNull(groups = {Save.class})
    private Category category;

    public interface Save {

    }
}
