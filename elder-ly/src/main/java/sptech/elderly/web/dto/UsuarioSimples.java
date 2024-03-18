package sptech.elderly.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.Usuario;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UsuarioSimples {
    private String nome;
    private String email;
    private String documento;
    private String senha;

    public UsuarioSimples(Usuario user) {
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.documento = user.getDocumento();
        this.senha = user.getSenha();
    }

    public static List<UsuarioSimples> toUserCliente(List<Usuario> users){
        return users.stream()
                .map(user -> new UsuarioSimples(user))
                .toList();
    }
}
