package sptech.elderly.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record UsuarioCreateDto(@NotBlank String nome, @Email @NotBlank String email, @NotBlank String senha,
                               @NotBlank String documento, Integer getGeneroId,
                               Integer getTipoUsuarioId) implements Serializable {
}