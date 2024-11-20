package sptech.elderly.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Endereco;
import sptech.elderly.service.EnderecoService;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;
import sptech.elderly.web.dto.endereco.EnderecoMapper;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @GetMapping("/{id}")
    public ResponseEntity<EnderecoOutput> atualizarEndereco(@PathVariable Long id){
        Endereco endereco = enderecoService.buscarEndereco(id);
        return status(200).body(EnderecoMapper.toDto(endereco));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnderecoOutput> buscarEnderecoPeloId(@PathVariable Long id, @RequestBody CriarEnderecoInput input){
        Endereco endereco = enderecoService.atualizarEnderecoMobile(id, input);
        return status(200).body(EnderecoMapper.toDto(endereco));
    }
}
