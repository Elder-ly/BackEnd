package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */

public record CriarUsuarioInput(@NotBlank String nome,
                                @Email @NotBlank String email,
                                @NotBlank String documento,
                                Integer tipoGenero,
                                Integer tipoUsuario) implements Serializable {
}