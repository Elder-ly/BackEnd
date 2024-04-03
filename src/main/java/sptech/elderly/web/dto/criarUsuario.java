package sptech.elderly.web.dto;

import jakarta.validation.constraints.NotBlank;

public class criarUsuario {
    @NotBlank
    private String nome;
    @NotBlank
    private String documento;
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}