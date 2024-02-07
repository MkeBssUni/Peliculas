package dwp.pelis.pelis.modules.movies.model;

import dwp.pelis.pelis.modules.categories.model.Category;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDto {
    private Long id;
    @NotNull(groups = {Save.class})
    private String name;
    @NotNull(groups = {Save.class})
    private String description;
    @NotNull(groups = {Save.class})
    private int releaseDate;
    @NotNull(groups = {Save.class})
    private Category category;

    public interface Save{}
}
