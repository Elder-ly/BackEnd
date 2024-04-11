package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.service.UsuarioService;
import sptech.elderly.web.dto.usuario.UsuarioSimples;
import sptech.elderly.web.dto.usuario.UsuarioCreateDto;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RequiredArgsConstructor
@RestController @RequestMapping("/usuarios")
public class UsuarioControllerBD {

    private final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<UsuarioCreateDto> criarUsuario(@RequestBody @Valid UsuarioCreateDto novoUser){
        usuarioService.salvar(novoUser);
        return status(HttpStatus.CREATED).build();
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioSimples>> buscarUsuarios(){
        var usuarios = usuarioService.buscarUsuarios();

        return usuarios.isEmpty()
                ? status(204).build()
                : status(200).body(UsuarioSimples.buscarUsuarios(usuarios));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<UsuarioEntity> buscarIdUsuario(@PathVariable Integer codigo){
        UsuarioEntity user = usuarioService.buscarPorId(codigo);
        return ok(user);
    }
}