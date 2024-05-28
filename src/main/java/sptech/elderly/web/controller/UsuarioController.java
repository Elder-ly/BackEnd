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

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cria um usuário do tipo cliente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso."),
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

    @Operation(summary = "Cria um usuário do tipo colaborador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Colaborador criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao criar colaborador."),
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

    @Operation(summary = "Busca todos os clientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum cliente encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping("/buscar-clientes")
    public ResponseEntity<List<UsuarioSimplesCliente>> buscarClientes() {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioSimplesCliente.buscarUsuarios(usuarios));
    }

    @Operation(summary = "Busca todos os colaboradores cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Colaboradores encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum colaborador encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping("/buscar-colaboradores")
    public ResponseEntity<List<ColaboradorOutput>> buscarColaboradores() {
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioMapper.ofColaborador(usuarios));
    }

    @Operation(summary = "Busca todos os usuários cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuários encontrados com sucesso."),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping
    public ResponseEntity<List<UsuarioConsultaDto>> buscarUsuarios(){
        List<UsuarioEntity> usuarios = usuarioService.buscarUsuarios();

        List<UsuarioConsultaDto> usuarioConsultaDto = UsuarioMapper.toDto(usuarios);

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(usuarioConsultaDto);
    }

    @Operation(summary = "Busca um usuário por ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping("/{codigo}")
    public ResponseEntity<UsuarioConsultaDto> buscarIdUsuario(@PathVariable Integer codigo){
        UsuarioConsultaDto usuario = usuarioService.buscarUsuarioId(codigo);
        return status(200).body(usuario);
    }

    @Operation(summary = "Busca um usuário por e-mail.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioConsultaDto> buscarPorEmail(@PathVariable String email){
        UsuarioConsultaDto user = usuarioService.buscarPorEmail(email);
        return status(200).body(user);
    }

    @Operation(summary = "Atualiza um usuário existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar usuário."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioConsultaDto> atualizarUsuario(@PathVariable Integer id, @RequestBody AtualizarUsuarioInput input){
        UsuarioEntity usuario = usuarioService.atualizarUsuario(id, input);
        return status(200).body(UsuarioMapper.toDto(usuario));
    }

    @Operation(summary = "Exclui um usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso."),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Integer id){
        usuarioService.excluirUsuario(id);
        return status(204).build();
    }

    @Operation(summary = "Baixa um arquivo CSV contendo os colaboradores.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo CSV baixado com sucesso."),
            @ApiResponse(responseCode = "503", description = "Serviço indisponível.")
    })
    @GetMapping(value = "colaboradores/csv", produces = "text/csv")
    public ResponseEntity<String> baixarCsvCuidadores(HttpServletResponse response) {
        response.setHeader("Content-Disposition", "inline; filename=cuidadores_elderly.csv");
        return ResponseEntity.status(200).body(usuarioService.gerarStringCsv());
    }

    @Operation(summary = "Retorna uma lista de todos os colaboradores disponíveis no horario e especialidades solicitadas")
    @GetMapping("/colaboradores")
    public ResponseEntity<List<UsuarioConsultaDto>> buscarCuidadoresPorEspecialidadeEDisponibilidade(@RequestHeader String accessToken, @RequestBody @Valid BuscarColaboradorInput input) throws GeneralSecurityException, IOException {
        return status(200).body(usuarioService.buscarColaboradoresPorEspecialidadeEDispoibilidade(accessToken, input));
    }
}