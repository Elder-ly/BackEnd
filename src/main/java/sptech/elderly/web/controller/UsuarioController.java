//package sptech.elderly.web.controller;
//
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//import sptech.elderly.classe.Usuario;
//import sptech.elderly.entity.UsuarioEntity;
//import sptech.elderly.web.dto.usuario.UsuarioSimples;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController @RequestMapping("api/v1/usuarios")
//public class UsuarioController {
//    private Integer proximoId = 1;
//    private List<Usuario> usuarios = new ArrayList<>();
//
//    private void validarEmail(String email){
//        if (!email.contains("@")){
//            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Email deve conter '@'");
//        }
//
//        if (usuarios.stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))){
//            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
//        }
//    }
//
//
//    @PostMapping
//    public ResponseEntity<Usuario> create(@RequestBody Usuario novoUser){
//        validarEmail(novoUser.getEmail());
//
//        novoUser.setId((long) proximoId++);
//        usuarios.add(novoUser);
//        return ResponseEntity.status(201).body(novoUser);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Usuario>> get(){
//        if (usuarios.isEmpty()){
//            return ResponseEntity.status(204).build();
//        }
//
//        return ResponseEntity.status(200).body(usuarios);
//    }
//
//    @GetMapping("/{userId}")
//    public ResponseEntity<Usuario> getById(@PathVariable int userId){
//        List<Usuario> encontrados = usuarios.stream()
//                .filter(u -> u.getId().equals(userId))
//                .toList();
//
//        if (encontrados.isEmpty()){
//            return ResponseEntity.status(404).build();
//        }
//
//        return ResponseEntity.status(200).body(encontrados.get(0));
//    }
//
//    @GetMapping("/cliente")
//    public ResponseEntity<List<UsuarioSimples>> getByClient(){
//        if (usuarios.isEmpty()){
//            return ResponseEntity.status(204).build();
//        }
//
//        return ResponseEntity.status(200).body(UsuarioSimples.toUserCliente(usuarios));
//    }
//
//    @PutMapping("/{userId}")
//    public ResponseEntity<Usuario> update(@PathVariable long userId, @RequestBody Usuario usuarioAtualizado){
//        validarEmail(usuarioAtualizado.getEmail());
//
//        for (int i = 0; i < usuarios.size(); i++) {
//            if (usuarios.get(i).getId().equals(userId)){
//                long idAntigo = usuarios.get(i).getId();
//                usuarios.set(i, usuarioAtualizado);
//                usuarioAtualizado.setId(idAntigo);
//                return ResponseEntity.status(200).body(usuarios.get(i));
//            }
//        }
//
//        return ResponseEntity.status(404).build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<UsuarioEntity> delete(@PathVariable int id){
//        for (int i = 0; i < usuarios.size(); i++) {
//            if (usuarios.get(i).getId().equals(id)){
//                usuarios.remove(i);
//                return ResponseEntity.status(204).build();
//            }
//        }
//
//        return ResponseEntity.status(404).build();
//    }
//}