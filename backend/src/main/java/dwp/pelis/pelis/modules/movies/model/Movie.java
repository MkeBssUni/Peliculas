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
    private String description;
    private int releaseDate;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public void save(MovieDto dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.releaseDate = dto.getReleaseDate();
        this.category = dto.getCategory();
    }
}
