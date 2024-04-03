package sptech.elderly.web.dto;

import jakarta.validation.constraints.NotBlank;

public class loginUsuario {
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}
