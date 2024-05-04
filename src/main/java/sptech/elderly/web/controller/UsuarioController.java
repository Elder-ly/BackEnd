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
            @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar cliente."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado."),
            @ApiResponse(responseCode = "422", description = "Entidade não processável.")
    })
    @PostMapping("/cliente")
    public ResponseEntity<CriarClienteInput> criarCliente(@RequestBody @Valid CriarClienteInput novoCliente){
        usuarioService.salvarCliente(novoCliente);
        return status(HttpStatus.CREATED).body(novoCliente);
    }

    @Operation(description = "Cria um usuário do tipo colaborador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaborador criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar Colaborador."),
            @ApiResponse(responseCode = "401", description = "Não autorizado."),
            @ApiResponse(responseCode = "403", description = "Acesso proibido."),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado."),
            @ApiResponse(responseCode = "422", description = "Entidade não processável.")
    })
    @PostMapping("/colaborador")
    public ResponseEntity<CriarColaboradorInput> criarFuncionario(@RequestBody @Valid CriarColaboradorInput novoUser){
        usuarioService.salvarColaborador(novoUser);
        return status(HttpStatus.CREATED).body(novoUser);
    }

    @GetMapping("/buscar-clientes")
    public ResponseEntity<List<UsuarioSimplesCliente>> buscarClientes() {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioSimplesCliente.buscarUsuarios(usuarios));
    }

    @GetMapping("/buscar-colaboradores")
    public ResponseEntity<List<UsuarioSimplesColaborador>> buscarColaboradores() {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioSimplesColaborador.buscarUsuarios(usuarios));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioConsultaDto>> buscarUsuarios(){
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioMapper.toDto(usuarios));
    }

    @GetMapping("/cliente/{codigo}")
    public ResponseEntity<UsuarioConsultaDto> buscarIdUsuario(@PathVariable Integer codigo){
        UsuarioEntity usuario = usuarioService.buscarUsuarioId(codigo);

        return status(200).body(UsuarioMapper.toDto(usuario));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioConsultaDto> buscarPorEmail(@PathVariable String email){
        UsuarioEntity user = usuarioService.buscarPorEmail(email);
        return status(200).body(UsuarioMapper.toDto(user));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<AtualizarClienteInput> atualizarUsuario(@PathVariable Integer id, @RequestBody @Valid AtualizarClienteInput input){
//        usuarioService.atualizarUsuario(id, input);
//        return status(200).body(input);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id){
        usuarioService.excluirCliente(id);
        return status(204).build();
    }
}