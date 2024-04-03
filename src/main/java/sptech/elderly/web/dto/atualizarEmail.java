package sptech.elderly.web.dto;

import jakarta.validation.constraints.NotBlank;

public class atualizarEmail {
    @NotBlank
    private String novoEmail;
}
