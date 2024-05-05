package sptech.elderly.web.dto.especialidade;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.Especialidade}
 */
public record AtualizarEspecialidade(
        @NotBlank String especialidade) implements Serializable {
}