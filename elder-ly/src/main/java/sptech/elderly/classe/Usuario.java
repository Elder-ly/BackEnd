package sptech.elderly.classe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter @Setter @NoArgsConstructor
public class Usuario {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank @Email
    private String email;

    @NotNull @Size(min = 8)
    private String senha;

    @CPF
    private String documento;

    @Valid
    private Especialidade especialidade;
}
