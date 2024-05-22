package sptech.elderly.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Email;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.service.EmailService;
import sptech.elderly.service.UsuarioService;
import sptech.elderly.web.dto.usuario.*;

import java.util.List;

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
    public ResponseEntity<UsuarioConsultaDto> criarCliente(@RequestBody @Valid CriarUsuarioInput novoCliente){
        UsuarioEntity usuario = usuarioService.salvarCliente(novoCliente);
        return status(201).body(UsuarioMapper.toDto(usuario));
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
    public ResponseEntity<UsuarioConsultaDto> criarFuncionario(@RequestBody @Valid CriarUsuarioInput novoUser){
        UsuarioEntity usuario = usuarioService.salvarColaborador(novoUser);
        return status(201).body(UsuarioMapper.toDto(usuario));
    }

    @GetMapping("/buscar-clientes")
    public ResponseEntity<List<UsuarioSimplesCliente>> buscarClientes() {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioSimplesCliente.buscarUsuarios(usuarios));
    }

    @GetMapping("/buscar-colaboradores")
    public ResponseEntity<List<ColaboradorOutput>> buscarColaboradores() {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioMapper.ofColaborador(usuarios));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioConsultaDto>> buscarUsuarios(){
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        List<UsuarioConsultaDto> usuarioConsultaDto = UsuarioMapper.toDto(usuarios);

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(usuarioConsultaDto);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<UsuarioConsultaDto> buscarIdUsuario(@PathVariable Integer codigo){
        UsuarioConsultaDto usuario = usuarioService.buscarUsuarioId(codigo);
        return status(200).body(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioConsultaDto> buscarPorEmail(@PathVariable String email){
        UsuarioConsultaDto user = usuarioService.buscarPorEmail(email);
        return status(200).body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioConsultaDto> atualizarUsuario(@PathVariable Integer id, @RequestBody AtualizarUsuarioInput input){
        UsuarioEntity usuario = usuarioService.atualizarUsuario(id, input);
        return status(200).body(UsuarioMapper.toDto(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id){
        usuarioService.excluirUsuario(id);
        return status(204).build();
    }

    @GetMapping(value = "colaboradores/csv", produces = "text/csv")
    public ResponseEntity<String> baixarCsvCuidadores(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "inline; filename=cuidadores_elderly.csv");
        return ResponseEntity.status(200).body(usuarioService.gerarStringCsv());
    }
}