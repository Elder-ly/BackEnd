package sptech.elderly.web.dto.especialidade;

import org.mapstruct.*;
import sptech.elderly.entity.Especialidade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EspecialidadeMapper {

    List<Especialidade> toEntities(List<String> nomes);

    Especialidade map(String nome);

    Especialidade toEntity(EspecialidadeOutput especialidadeOutput);

    EspecialidadeOutput toDto(Especialidade especialidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Especialidade partialUpdate(EspecialidadeOutput especialidadeOutput, @MappingTarget Especialidade especialidade);

    Especialidade toUpdate(AtualizarEspecialidade input);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Especialidade partialUpdate(AtualizarEspecialidade especialidadeInput, @MappingTarget Especialidade especialidade);
}
