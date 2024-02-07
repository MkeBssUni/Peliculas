package dwp.pelis.pelis.modules.categories.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dwp.pelis.pelis.modules.movies.model.Movie;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Movie> movies;

    public void save(CategoryDto dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
    }

}
