package sptech.elderly.web.dto;

import jakarta.validation.constraints.NotBlank;

public class atualizarSenha {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novasenha;

    @NotBlank
    private String confirmarSenha;
}
