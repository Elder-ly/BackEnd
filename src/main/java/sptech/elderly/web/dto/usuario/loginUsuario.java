package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public class loginUsuario {
    @NotBlank
    private String email;
    @NotBlank
    private String senha;
}
