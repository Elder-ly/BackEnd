package sptech.elderly.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.service.UsuarioService;
import sptech.elderly.web.dto.usuario.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController @RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(description = "Cria um usuário do tipo cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar usuário."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado."),
            @ApiResponse(responseCode = "422", description = "Entidade não processável."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @PostMapping("/cliente")
    public ResponseEntity<CriarClienteInput> criarCliente(@RequestBody @Valid CriarClienteInput novoUser){
        this.usuarioService.salvarCliente(novoUser);
        return status(HttpStatus.CREATED).body(novoUser);
    }

    @Operation(description = "Cria um funcionário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar Funcionário."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado."),
            @ApiResponse(responseCode = "422", description = "Entidade não processável."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @PostMapping("/funcionario")
    public ResponseEntity<CriarFuncionarioInput> criarFuncionario(@RequestBody @Valid CriarFuncionarioInput novoUser){
        this.usuarioService.salvarFuncionario(novoUser);
        return status(HttpStatus.CREATED).body(novoUser);
    }


    @GetMapping("/buscar-clientes")
    public ResponseEntity<List<UsuarioSimplesCliente>> buscarClientes() {
        var usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioSimplesCliente.buscarUsuarios(usuarios));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioConsultaDto>> buscarUsuarios(){
        var usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioMapper.toDto(usuarios));
    }

    @GetMapping("/cliente/{codigo}")
    public ResponseEntity<UsuarioConsultaDto> buscarIdUsuario(@PathVariable Integer codigo){
        var usuario = usuarioService.buscarUsuarioId(codigo);

        return ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioConsultaDto> buscarPorEmail(@PathVariable String email){
        UsuarioEntity user = usuarioService.buscarPorEmail(email);
        return ok(UsuarioMapper.toDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtualizarClienteInput> atualizarUsuario(@PathVariable Integer id, @RequestBody @Valid AtualizarClienteInput input){
        usuarioService.atualizarUsuario(id, input);
        return status(200).body(input);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id){
        usuarioService.excluirCliente(id);
        return status(204).build();
    }
}