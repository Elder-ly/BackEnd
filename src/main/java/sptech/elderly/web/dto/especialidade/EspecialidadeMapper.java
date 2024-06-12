package sptech.elderly.web.dto.especialidade;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.Especialidade;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor
public class EspecialidadeMapper {

    private final ModelMapper mapper;

    public List<Especialidade> toEntities(List<String> nomes){
        return nomes.stream()
                .map(nome -> {
                    Especialidade especialidade = new Especialidade();
                    especialidade.setNome(nome);
                    return especialidade;
                })
                .collect(Collectors.toList());
    }

    public EspecialidadeOutput toDto(Especialidade especialidade){
        return mapper.map(especialidade, EspecialidadeOutput.class);
    }

    public List<EspecialidadeOutput> toDtos(List<Especialidade> especialidades) {
        return especialidades.stream()
                .map(especialidade -> new EspecialidadeOutput(
                        especialidade.getId(),
                        especialidade.getNome()
                ))
                .collect(Collectors.toList());
    }
}