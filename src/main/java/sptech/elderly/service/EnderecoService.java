package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.Residencia;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.repository.EnderecoRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.endereco.AtualizarEnderecoInput;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.endereco.EnderecoMapper;

@Service @RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    private final EnderecoMapper enderecoMapper;

    public Endereco salvar(CriarEnderecoInput novoEndereco) {
        return this.enderecoRepository.save(enderecoMapper.ofEndereco(novoEndereco));
    }

    public Endereco atualizarEndereco(Integer id, AtualizarEnderecoInput input) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Endereço não encontrado"));

        return enderecoMapper.partialUpdate(input, endereco);
    }

    public void excluirEndereco(Integer id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Endereço não encontrado"));

        if (endereco.getResidencias() != null){
            endereco.getResidencias()
                    .forEach(residencia -> {
                        enderecoRepository.delete(residencia.getEndereco());
                    });
        }

        enderecoRepository.delete(endereco);
    }
}
