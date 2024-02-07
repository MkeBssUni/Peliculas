package dwp.pelis.pelis.modules.categories.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryDto {

    private Long id;
    @NotNull(groups = {Save.class})
    private String name;
    @NotNull(groups = {Save.class})
    private String description;

    public interface Save{}
}
