package sptech.elderly.web.dto;

import jakarta.validation.constraints.NotBlank;

public class atualizarDocumento {
    @NotBlank
    private String documento;
}
