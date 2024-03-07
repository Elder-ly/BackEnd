package Elder.Connect.elderconnect.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor @NoArgsConstructor @Builder @Setter @Getter @ToString
public class Usuario {

    // @Id
    // GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column (name = "id")
    @NotNull
    private Long id;

    @NotBlank
    //Column (name = "username", nulllable = false, unique = true, length = 100)
    private String username;

    //Column (name = "password", nulllable = false, length = 200)
    private String password;
}
