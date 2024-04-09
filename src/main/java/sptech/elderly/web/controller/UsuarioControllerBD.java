package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.service.UsuarioService;
import sptech.elderly.web.dto.usuario.UsuarioCreateDto;

@RequiredArgsConstructor
@RestController @RequestMapping("/usuarios")
public class UsuarioControllerBD {

    private final UsuarioService usuarioService;

    @PostMapping()
    public ResponseEntity<UsuarioCreateDto> criarUsuario(@RequestBody @Valid UsuarioCreateDto novoUser){
        usuarioService.salvar(novoUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UsuarioEntity> getById(@PathVariable Integer userId){
        UsuarioEntity user = usuarioService.buscarPorId(userId);
        return ResponseEntity.ok(user);
    }
}