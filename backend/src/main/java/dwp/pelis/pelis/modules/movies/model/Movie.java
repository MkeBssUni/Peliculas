package dwp.pelis.pelis.modules.movies.model;

import dwp.pelis.pelis.modules.categories.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String director;
    private int releaseDate;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void save(MovieDto dto){
        this.name = dto.getName();
        this.director = dto.getDirector();
        this.releaseDate = dto.getReleaseDate();
        this.description = dto.getDescription();
        this.category = dto.getCategory();
    }
}
