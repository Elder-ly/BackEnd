package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.EnderecoService;
import sptech.elderly.service.UsuarioService;
import sptech.elderly.web.dto.endereco.AtualizarEnderecoInput;
import sptech.elderly.web.dto.usuario.*;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController @RequestMapping("/enderecos")
public class EnderecoController {

    private final UsuarioService usuarioService;

//    @PutMapping("/{id}")
//    public ResponseEntity<AtualizarEnderecoInput> atualizarEndereco(@PathVariable Integer id, @RequestBody @Valid AtualizarEnderecoInput input){
//        usuarioService.atualizarEndereco(id, input);
//        return status(200).body(input);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> excluirEndereco(@PathVariable Integer id){
//        usuarioService.excluirEndereco(id);
//        return status(204).build();
//    }
}