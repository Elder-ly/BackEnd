package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.entity.Usuario;
import sptech.elderly.web.dto.UsuarioSimples;

import java.util.ArrayList;
import java.util.List;

@RestController @RequestMapping("/user")
public class UsuarioController {
    private Integer proximoId = 1;
    private List<Usuario> users = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario novoUser){

        novoUser.setId((long) proximoId++);
        users.add(novoUser);
        return ResponseEntity.status(201).body(novoUser);
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsers(){
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Usuario> getById(@PathVariable int userId){
        if (userId < 0 || userId >= users.size()){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(users.get(userId));
    }

    @GetMapping("/cliente")
    public ResponseEntity<List<UsuarioSimples>> getByClient(){
        if (users.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(UsuarioSimples.toUserCliente(users));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Usuario> update(@PathVariable int userId, @RequestBody Usuario updateuser){
        if (userId < 0 || userId >= users.size()){
            return ResponseEntity.status(404).build();
        }

        users.set(userId, updateuser);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Usuario> delete(@PathVariable int userId){
        if (userId < 0 || userId >= users.size()){
            return ResponseEntity.status(404).build();
        }

        users.remove(userId);
        return ResponseEntity.status(204).build();
    }

}