package sptech.elderly.web.dto.especialidade;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.Especialidade;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor @Component
public class EspecialidadeMapper {

    private final ModelMapper mapper;

    public List<Especialidade> toEntities(List<String> nomes){
        return nomes.stream()
                .map(nome -> mapper.map(nome, Especialidade.class))
                .collect(Collectors.toList());
    }

    public EspecialidadeOutput toDto(Especialidade especialidade){
        return mapper.map(especialidade, EspecialidadeOutput.class);
    }
}