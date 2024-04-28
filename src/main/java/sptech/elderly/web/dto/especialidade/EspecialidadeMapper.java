package sptech.elderly.web.dto.especialidade;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import sptech.elderly.entity.Especialidade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EspecialidadeMapper {

    Especialidade map(String nome);

    public List<Especialidade> criarEspecialidade(List<String> especialidades);

    Especialidade toEntity(EspecialidadeConsultaDto especialidadeConsultaDto);

    EspecialidadeConsultaDto toDto(Especialidade especialidade);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Especialidade partialUpdate(EspecialidadeConsultaDto especialidadeConsultaDto, @MappingTarget Especialidade especialidade);
}
