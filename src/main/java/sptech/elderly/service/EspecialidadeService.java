package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.repository.EspecialidadeRepository;
import sptech.elderly.web.dto.especialidade.EspecialidadeMapper;

import java.util.List;

@Service @RequiredArgsConstructor
public class EspecialidadeService {

    private final EspecialidadeRepository especialidadeRepository;
    private final EspecialidadeMapper especialidadeMapper;

    public List<Especialidade> salvar(List<String> especialidadesNomes) {
        List<Especialidade> especialidades = especialidadeMapper.criarEspecialidade(especialidadesNomes);
        return this.especialidadeRepository.saveAll(especialidades);
    }
}
