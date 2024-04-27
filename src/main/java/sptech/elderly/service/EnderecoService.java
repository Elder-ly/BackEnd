package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Endereco;
import sptech.elderly.repository.EnderecoRepository;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.endereco.EnderecoMapper;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    public Endereco salvar(CriarEnderecoInput novoEndereco) {
        return this.enderecoRepository.save(enderecoMapper.ofEndereco(novoEndereco));
    }
}
