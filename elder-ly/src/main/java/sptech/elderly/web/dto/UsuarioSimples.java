package sptech.elderly.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import sptech.elderly.entity.Usuario;

import java.util.List;

@Getter @NoArgsConstructor
public class UsuarioSimples {
    private String nome;
    private String email;
    private String documento;

    public UsuarioSimples(Usuario user) {
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.documento = user.getDocumento();
    }

    public static List<UsuarioSimples> toUserCliente(List<Usuario> users){
        return users.stream()
                .filter(user -> user.getEspecialidade().equals(null))
                .map(user -> new UsuarioSimples(user))
                .toList();
    }
}
