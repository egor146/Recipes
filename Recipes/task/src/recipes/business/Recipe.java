package recipes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @JsonIgnore
    @Id
    @Column(name = "RecipeID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User user;

    @NotBlank
    private String name;

    @NotBlank
    private String category;

    private LocalDateTime date;

    @NotBlank
    private String description;

    @Size(min = 1)
    @NotEmpty
    private String[] ingredients;

    @Size(min = 1)
    @NotEmpty
    private String[] directions;

}
