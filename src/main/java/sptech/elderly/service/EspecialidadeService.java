package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.repository.CurriculoRepository;
import sptech.elderly.repository.EspecialidadeRepository;
import sptech.elderly.web.dto.especialidade.AtualizarEspecialidade;
import sptech.elderly.web.dto.especialidade.CriarEspecialidadeInput;
import sptech.elderly.web.dto.especialidade.EspecialidadeMapper;
import sptech.elderly.web.dto.especialidade.EspecialidadeOutput;

import java.util.List;

@Service @RequiredArgsConstructor
public class EspecialidadeService {

    private final CurriculoService curriculoService;

    private final EspecialidadeMapper especialidadeMapper;
    private final EspecialidadeRepository especialidadeRepository;
    private final CurriculoRepository curriculoRepository;

    public List<Especialidade> salvar(CriarEspecialidadeInput input) {

        List<Especialidade> especialidades = especialidadeMapper.toEntities(input.especialidades());

        List<Especialidade> especialidadesCriadas = especialidadeRepository.saveAll(especialidades);

        curriculoService.salvarEspecialidades(especialidadesCriadas);

        return especialidadesCriadas;
    }

    @Transactional
    public Especialidade atualizarEspecialidade(Integer id, AtualizarEspecialidade input) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada"));

        especialidade.setNome(input.especialidade());

        return especialidadeRepository.save(especialidade);
    }

    public EspecialidadeOutput buscarEspecialidade(Integer id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada"));

        return especialidadeMapper.toDto(especialidade);
    }

    public void deletarEspecialidade(Integer id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada"));

        curriculoService.excluirEspecialidade(especialidade);

        especialidadeRepository.delete(especialidade);
    }
}