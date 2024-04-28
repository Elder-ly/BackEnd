package sptech.elderly.web.dto.especialidade;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.Especialidade}
 */
@Getter @Setter @NoArgsConstructor
public class EspecialidadeConsultaDto implements Serializable {
    String nome;
}