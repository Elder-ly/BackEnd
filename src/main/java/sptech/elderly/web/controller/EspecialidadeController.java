package sptech.elderly.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.service.EspecialidadeService;
import sptech.elderly.web.dto.especialidade.AtualizarEspecialidade;
import sptech.elderly.web.dto.especialidade.CriarEspecialidadeInput;
import sptech.elderly.web.dto.especialidade.EspecialidadeOutput;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/especialidades")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeService;

    @Operation(summary = "Cria uma nova especialidade.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Especialidade criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na criação da especialidade."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "422", description = "Entidade não processável.")
    })
    @PostMapping
    public ResponseEntity<CriarEspecialidadeInput> criarEspecialidade(@RequestBody @Valid CriarEspecialidadeInput novaEspecialidade){
        especialidadeService.salvar(novaEspecialidade);
        return status(201).body(novaEspecialidade);
    }

    @Operation(summary = "Busca uma especialidade por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade encontrada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<EspecialidadeOutput> buscarEspecialidade(@PathVariable Integer id){
        return ok(especialidadeService.buscarEspecialidade(id));
    }

    @Operation(summary = "Atualiza uma especialidade existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidade atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro na atualização da especialidade."),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Especialidade> atualizarEspecialidade(@PathVariable Integer id, @RequestBody @Valid AtualizarEspecialidade input){
        Especialidade especialidade = especialidadeService.atualizarEspecialidade(id, input);
        return status(200).body(especialidade);
    }

    @Operation(summary = "Exclui uma especialidade.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Especialidade excluída com sucesso."),
            @ApiResponse(responseCode = "404", description = "Especialidade não encontrada."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirEspecialidade(@PathVariable Integer id){
        especialidadeService.deletarEspecialidade(id);
        return status(204).build();
    }

    @Operation(summary = "Busca todas as especialidades cadastradas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Especialidades encontradas com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhuma especialidade encontrada."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping
    public ResponseEntity<List<Especialidade>> buscarEspecialidades(){
        return status(200).body(especialidadeService.buscarEspecialidade());
    }
}