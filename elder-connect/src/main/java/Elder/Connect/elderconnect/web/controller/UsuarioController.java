package Elder.Connect.elderconnect.web.controller;

import Elder.Connect.elderconnect.entity.Usuario;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController @RequestMapping("/user")
public class UsuarioController {

    private List<Usuario> usuarios = new ArrayList<>();

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario novoUser){
        usuarios.add(novoUser);

        return ResponseEntity.status(201).body(novoUser);
    }

    @GetMapping()
    public ResponseEntity<List<Usuario>> getUsers(){
        if (usuarios.isEmpty()){
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.status(200).body(usuarios);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Usuario> getById(@PathVariable int userId){
        if (userId < 0 || userId >= usuarios.size()){
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.status(200).body(usuarios.get(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Usuario> update(@PathVariable int userId, @RequestBody Usuario user){
        if (userId < 0 || userId >= usuarios.size()){
            return ResponseEntity.status(404).build();
        }

        usuarios.set(userId, user);
        return ResponseEntity.status(204).build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Usuario> delete(@PathVariable int userId){
        if (userId < 0 || userId >= usuarios.size()){
            return ResponseEntity.status(404).build();
        }

        usuarios.remove(userId);
        return ResponseEntity.status(204).build();
    }
}