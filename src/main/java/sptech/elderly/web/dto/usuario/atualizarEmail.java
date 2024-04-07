package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public class atualizarEmail {
    @NotBlank
    private String novoEmail;
}
