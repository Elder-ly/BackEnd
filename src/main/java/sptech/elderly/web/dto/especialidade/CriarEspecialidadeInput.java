package sptech.elderly.web.dto.especialidade;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.Especialidade}
 */
public record CriarEspecialidadeInput(@NotBlank String nome) implements Serializable {
}