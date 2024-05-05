package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.service.EspecialidadeService;
import sptech.elderly.service.UsuarioService;
import sptech.elderly.web.dto.especialidade.AtualizarEspecialidade;
import sptech.elderly.web.dto.especialidade.CriarEspecialidadeInput;
import sptech.elderly.web.dto.especialidade.EspecialidadeOutput;

import static org.springframework.http.ResponseEntity.*;

@RequiredArgsConstructor
@RestController @RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    @PostMapping
    public ResponseEntity<CriarEspecialidadeInput> criarEspecialidade(@RequestBody @Valid CriarEspecialidadeInput novaEspecialidade){
        especialidadeService.salvar(novaEspecialidade);
        return status(201).body(novaEspecialidade);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeOutput> buscarEspecialidade(@PathVariable Integer id){
        return ok(especialidadeService.buscarEspecialidade(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Especialidade> atualizarEndereco(@PathVariable Integer id, @RequestBody @Valid AtualizarEspecialidade input){
        Especialidade especialidade = especialidadeService.atualizarEspecialidade(id, input);
        return status(200).body(especialidade);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEndereco(@PathVariable Integer id){
        especialidadeService.deletarEspecialidade(id);
        return status(204).build();
    }
}