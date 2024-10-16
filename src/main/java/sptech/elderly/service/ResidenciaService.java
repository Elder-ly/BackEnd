package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.Residencia;
import sptech.elderly.entity.Usuario;
import sptech.elderly.repository.ResidenciaRepository;

@Service @RequiredArgsConstructor
public class ResidenciaService {
    private final ResidenciaRepository residenciaRepository;

    public Residencia salvar(Usuario novoUsuario, Endereco novoEndereco) {
        Residencia residencia = new Residencia();

        residencia.setUsuario(novoUsuario);
        residencia.setEndereco(novoEndereco);

        return residenciaRepository.save(residencia);
    }
}