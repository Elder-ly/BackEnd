package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Lucro;
import sptech.elderly.service.LucroService;
import sptech.elderly.web.dto.lucro.AtualizarLucroInput;
import sptech.elderly.web.dto.lucro.CriarLucroInput;
import sptech.elderly.web.dto.lucro.LucroMapper;
import sptech.elderly.web.dto.lucro.LucroOutput;

import java.util.List;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/lucros")
public class LucroController {

    private final LucroService service;

    @PostMapping
    public ResponseEntity<LucroOutput> criarLucro(@RequestBody @Valid CriarLucroInput novoLucro){
        Lucro lucro = service.salvarLucro(novoLucro);
        return status(201).body(LucroMapper.toDto(lucro));
    }

    @GetMapping()
    public ResponseEntity<List<LucroOutput>> buscarLucros(){
        List<Lucro> lucros = service.findLucros();

        return lucros.isEmpty()
                ? status(204).build()
                : status(200).body(LucroMapper.ofLucros(lucros));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<LucroOutput> buscarLucroPorId(@PathVariable Long codigo){
        Lucro lucro = service.buscarLucro(codigo);
        return status(200).body(LucroMapper.toDto(lucro));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<LucroOutput> atualizarLucro(@RequestBody @Valid AtualizarLucroInput input, @PathVariable Long codigo){
        LucroOutput lucro = LucroMapper.toDto(service.atualizarLucro(codigo, input));
        return status(200).body(lucro);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> excluirLucro(@PathVariable Long codigo){
        service.deletarLucro(codigo);
        return status(204).build();
    }
}
