package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;

public class atualizarDocumento {
    @NotBlank
    private String documento;
}
