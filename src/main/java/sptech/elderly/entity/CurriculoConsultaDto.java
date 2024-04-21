package sptech.elderly.entity;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link Curriculo}
 */
@Value
public class CurriculoConsultaDto implements Serializable {
    Especialidade especialidade;

    public CurriculoConsultaDto(Curriculo curriculo) {
        this.especialidade = curriculo.getEspecialidade();
    }

    public static List<CurriculoConsultaDto> converterLista(List<Curriculo> curriculos) {
        return curriculos.stream()
                .map(CurriculoConsultaDto::new)
                .toList();
    }
}