package sptech.elderly.classe;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Especialidade {
    @NotBlank
    private String nome;
}
