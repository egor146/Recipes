package recipes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Recipe> recipes = new ArrayList<>();

    @NotBlank
    @Email
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;

    @JsonIgnore
    private String roles;

    @JsonIgnore
    private boolean active;
}
