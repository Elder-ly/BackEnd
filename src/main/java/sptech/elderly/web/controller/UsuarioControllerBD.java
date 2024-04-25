package sptech.elderly.web.controller;

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
public class UsuarioControllerBD {

    private final UsuarioService usuarioService;

    @PostMapping("/cliente")
    public ResponseEntity<CriarCliente> criarCliente(@RequestBody @Valid CriarCliente novoUser){

        this.usuarioService.salvarCliente(novoUser);
        return status(HttpStatus.CREATED).body(novoUser);
    }

    @PostMapping("/funcionario")
    public ResponseEntity<CriarFuncionario> criarFuncionario(@RequestBody @Valid CriarFuncionario novoUser){

//        this.usuarioService.salvarFuncionario(novoUser);
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

    @GetMapping("/{codigo}")
    public ResponseEntity<UsuarioConsultaDto> buscarIdUsuario(@PathVariable Integer codigo){
        var usuario = usuarioService.buscarPorId(codigo);

        return ok(usuario);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioEntity> buscarPorEmail(@PathVariable String email){
        UsuarioEntity user = usuarioService.buscarPorEmail(email);
        return ok(user);
    }

    @PatchMapping()
    public ResponseEntity<UsuarioEntity> atualizarUsuario(){
        return null;
    }
}