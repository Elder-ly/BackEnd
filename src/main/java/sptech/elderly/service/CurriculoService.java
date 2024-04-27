package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.CurriculoRepository;

@Service @RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    public void associarEspecialidadeUsuario(UsuarioEntity novoUsuario, Especialidade especialidade) {
        Curriculo curriculo = new Curriculo();

        curriculo.setUsuario(novoUsuario);
        curriculo.setEspecialidade(especialidade);

        curriculoRepository.save(curriculo);
    }
}
