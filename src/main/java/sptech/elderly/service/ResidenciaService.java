package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.Residencia;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.ResidenciaRepository;

@Service @RequiredArgsConstructor
public class ResidenciaService {

    private final ResidenciaRepository residenciaRepository;

    public Residencia salvar(UsuarioEntity novoUsuario, Endereco endereco) {
        Residencia residencia = new Residencia();

        residencia.setUsuario(novoUsuario);
        residencia.setEndereco(endereco);

        return residenciaRepository.save(residencia);
    }
}
