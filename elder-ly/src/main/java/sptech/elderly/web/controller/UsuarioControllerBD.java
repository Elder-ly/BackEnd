//package sptech.elderly.web.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import sptech.elderly.entity.UsuarioEntity;
//import sptech.elderly.service.UsuarioService;
//
//@RequiredArgsConstructor
//@RestController @RequestMapping("api/v1/usuarios")
//public class UsuarioControllerBD {
//    private final UsuarioService usuarioService;
//
//    @PostMapping
//    public ResponseEntity<UsuarioEntity> create(@RequestBody UsuarioEntity novoUser){
//        UsuarioEntity user = usuarioService.salvar(novoUser);
//        return ResponseEntity.status(HttpStatus.CREATED).body(user);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<UsuarioEntity> getById(@PathVariable Long userId){
//        UsuarioEntity user = usuarioService.buscarPorId(userId);
//        return ResponseEntity.ok(user);
//    }
//}