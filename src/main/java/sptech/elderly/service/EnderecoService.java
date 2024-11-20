package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Endereco;
import sptech.elderly.exceptions.RecursoNaoEncontradoException;
import sptech.elderly.repository.EnderecoRepository;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.endereco.EnderecoMapper;

@Service @RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final ResidenciaService residenciaService;

    public Endereco salvar(CriarEnderecoInput novoEndereco) {
        return this.enderecoRepository.save(enderecoMapper.mapearEndereco(novoEndereco));
    }

    public Endereco atualizarEndereco(Long id, CriarEnderecoInput input) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço", id));

        endereco.setId(id);
        endereco = EnderecoMapper.atualizarEndereco(input, id);

        return enderecoRepository.save(endereco);
    }

    public void excluirEndereco(Long id) {
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

    public Endereco atualizarEnderecoMobile(Long codigoUser, CriarEnderecoInput input) {
        Long enderecoId = residenciaService.buscarEnderecoByUser(codigoUser);

        Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço", enderecoId));
        endereco.setId(enderecoId);

        if (input.cep() != null){
            endereco.setCep(input.cep());
        }

        if (input.logradouro() != null){
            endereco.setLogradouro(input.logradouro());
        }

        if (input.complemento() != null){
            endereco.setComplemento(input.complemento());
        }

        if (input.bairro() != null){
            endereco.setBairro(input.bairro());
        }

        if (input.numero() != null){
            endereco.setNumero(input.numero());
        }

        if (input.cidade() != null){
            endereco.setCidade(input.cidade());
        }

        if (input.uf() != null){
            endereco.setUf(input.uf());
        }

        enderecoRepository.save(endereco);
        return endereco;
    }

    public Endereco buscarEndereco(Long codigoUser) {
        Long enderecoId = residenciaService.buscarEnderecoByUser(codigoUser);

        return enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Endereço", enderecoId));
    }
}
