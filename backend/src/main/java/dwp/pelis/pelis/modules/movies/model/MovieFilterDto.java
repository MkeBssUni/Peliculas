package dwp.pelis.pelis.modules.movies.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieFilterDto {
    private String name;
    private String director;
    private Integer startYear;
    private Integer endYear;
    private Long category;
    @NotBlank(groups = {Get.class})
    private String order;

    public interface Get {

    }
}
