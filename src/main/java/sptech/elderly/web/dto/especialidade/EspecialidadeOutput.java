package sptech.elderly.web.dto.especialidade;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.entity.Especialidade;

import java.io.Serializable;

/**
 * DTO for {@link Especialidade}
 */
public record EspecialidadeOutput(@NotNull Integer id,
                                  @NotBlank String nome) implements Serializable {
}