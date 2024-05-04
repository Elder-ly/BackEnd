package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.CurriculoRepository;

import java.util.List;

@Service @RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;

    public void salvarEspecialidades(List<Especialidade> especialidades) {
        Curriculo curriculo = new Curriculo();

        curriculo.setEspecialidade(especialidades.get(0));

        curriculoRepository.save(curriculo);
    }

    public void excluirEspecialidade(Especialidade especialidade) {
        Curriculo curriculo = curriculoRepository.findByEspecialidade(especialidade);

        curriculoRepository.delete(curriculo);
    }
}
