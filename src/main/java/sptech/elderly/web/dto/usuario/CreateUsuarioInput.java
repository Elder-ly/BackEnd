package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */

public record CreateUsuarioInput(@NotBlank String nome,
                                 @Email @NotBlank String email,
                                 @NotBlank @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*()-+=])[A-Za-z\\d!@#$%^&*()-+=]{8}$") String senha,
                                 @NotBlank String documento,
                                 Integer getGeneroId,
                                 Integer getTipoUsuarioId) implements Serializable {
}