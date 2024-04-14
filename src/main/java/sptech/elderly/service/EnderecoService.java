package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sptech.elderly.entity.Endereco;
import sptech.elderly.repository.EnderecoRepository;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.endereco.EnderecoMapper;

@Service @RequiredArgsConstructor
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco salvar(CriarEnderecoInput novoEndereco) {
        Endereco endereco = EnderecoMapper.ofEndereco(novoEndereco);
        return this.enderecoRepository.save(endereco);
    }
}
