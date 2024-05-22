package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Endereco;
import sptech.elderly.repository.EnderecoRepository;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.endereco.EnderecoMapper;

@Service @RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public Endereco salvar(CriarEnderecoInput novoEndereco) {
        return this.enderecoRepository.save(enderecoMapper.mapearEndereco(novoEndereco));
    }

    public Endereco atualizarEndereco(Integer id, CriarEnderecoInput input) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Endereço não encontrado"));

        endereco = enderecoMapper.mapearEndereco(input);

        return enderecoRepository.save(endereco);
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
