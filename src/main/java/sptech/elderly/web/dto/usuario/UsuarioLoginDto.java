package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record UsuarioLoginDto(@Email @NotBlank String email,
                              @NotBlank String senha) implements Serializable {
}