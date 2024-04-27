package sptech.elderly.web.dto.especialidade;

import org.mapstruct.Mapper;
import sptech.elderly.entity.Especialidade;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EspecialidadeMapper {

    Especialidade map(String nome);

    public List<Especialidade> criarEspecialidade(List<String> especialidades);
}
