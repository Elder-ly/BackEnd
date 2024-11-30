package sptech.elderly.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.repository.EspecialidadeRepository;
import sptech.elderly.util.FilaObj;
import sptech.elderly.web.dto.especialidade.AtualizarEspecialidade;
import sptech.elderly.web.dto.especialidade.CriarEspecialidadeInput;
import sptech.elderly.web.dto.especialidade.EspecialidadeMapper;
import sptech.elderly.web.dto.especialidade.EspecialidadeOutput;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class EspecialidadeService {
    private final CurriculoService curriculoService;

    private final EspecialidadeMapper especialidadeMapper;

    private final EspecialidadeRepository especialidadeRepository;

    public List<Especialidade> salvar(CriarEspecialidadeInput input) {
        List<Especialidade> especialidades = especialidadeMapper.toEntities(input.especialidades());
        return especialidadeRepository.saveAll(especialidades);
    }

    @Transactional
    public Especialidade atualizarEspecialidade(Long id, AtualizarEspecialidade input) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada"));
        especialidade.setId(id);
        especialidade.setNome(input.especialidade());
        return especialidadeRepository.save(especialidade);
    }

    public EspecialidadeOutput buscarEspecialidade(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada"));

        return especialidadeMapper.toDto(especialidade);
    }

    public void deletarEspecialidade(Long id) {
        Especialidade especialidade = especialidadeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada"));

        curriculoService.excluirEspecialidade(id);
        especialidadeRepository.delete(especialidade);
    }

    public List<EspecialidadeOutput> buscarEspecialidades() {
        return especialidadeMapper.toDtos(especialidadeRepository.findAll());
    }

    public Object[] buscarEspecialidadesFila(){
        List<EspecialidadeOutput> especialidadeOutputs = buscarEspecialidades().stream()
                .sorted(Comparator.comparingLong(EspecialidadeOutput::id))
                .toList();

        FilaObj<EspecialidadeOutput> filaEspecialidades = new FilaObj<>(especialidadeOutputs.size());

        especialidadeOutputs.forEach(filaEspecialidades::insert);

        return filaEspecialidades.getFila();
    }
}